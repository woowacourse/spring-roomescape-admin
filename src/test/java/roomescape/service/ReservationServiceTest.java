package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.fixture.ReservationFixture.INITIAL_RESERVATION_SIZE;
import static roomescape.fixture.ReservationFixture.RESERVATION_1_ID;
import static roomescape.fixture.ReservationFixture.RESERVATION_2_ID;
import static roomescape.fixture.ReservationSlotFixture.INITIAL_SLOT_SIZE;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import roomescape.domain.Member;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationStatus;
import roomescape.exception.BadRequestException;
import roomescape.exception.ErrorCode;
import roomescape.fixture.MemberFixture;
import roomescape.fixture.ReservationFixture;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationSlotRepository;
import roomescape.service.dto.ReservationAdminRequest;
import roomescape.service.dto.ReservationMineResponse;
import roomescape.service.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private ReservationSlotRepository slotRepository;

    @Test
    @DisplayName("두 개의 스레드가 동시에 슬롯 생성을 요청하면 DataIntegrityViolationException을 원인으로 한 예외가 발생한다.")
    void createFailByDataIntegrityViolationException() throws InterruptedException {
        // given
        SlotCreator slotCreator1 = new SlotCreator(reservationService, transactionManager);
        SlotCreator slotCreator2 = new SlotCreator(reservationService, transactionManager);
        Thread thread1 = new Thread(slotCreator1, "request1");
        Thread thread2 = new Thread(slotCreator2, "request2");

        thread1.start();

        // when
        // 여기서 무조건 예외가 터지긴 하는데, DataIntegrityViolationException를 cause로 갖고있는지가 관건
        Thread.sleep(100); // thread2가 100ms 늦게 출발
        thread2.start();

        // 테스트 메서드가 thread1, thread2보다 더 빨리 끝나는 것을 방지
        thread1.join();
        thread2.join();

        // then: thread1은 예약 성공, thread2는 예약 실패
        assertThat(slotRepository.findAll().size()).isEqualTo(INITIAL_SLOT_SIZE + 1);
        assertThat(reservationRepository.findAll().size()).isEqualTo(INITIAL_RESERVATION_SIZE + 1);
        assertThat(slotCreator2.getException().getCause()).isInstanceOf(DataIntegrityViolationException.class);
    }

    static class SlotCreator implements Runnable {

        private final ReservationService reservationService;
        private final PlatformTransactionManager transactionManager;
        private Exception e;

        public SlotCreator(ReservationService reservationService, PlatformTransactionManager transactionManager) {
            this.reservationService = reservationService;
            this.transactionManager = transactionManager;
        }

        @Override
        public void run() {
            try {
                // thread1의 트랜잭션이 커밋되기 전에 thread2의 트랜잭션이 시작하여
                // reservationSlotRepository.existsByDateAndThemeIdAndTimeId() 메서드를 통과하게 되고 insert 쿼리까지 날린다.
                // 하지만 thread1이 커밋된 후 thread2가 커밋을 시도할 때 DataIntegrityViolationException이 발생한다.
                TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
                reservationService.createReservedReservation(ReservationFixture.newRequest(), MemberFixture.member1());
                // unique 조건 때문에 락이 걸려있어서 스레드2가 여기까지도 못 온다.
                // 커밋을 시도하기 전에, insert 쿼리에서 이미 예외가 발생한다.
                Thread.sleep(200);
                transactionManager.commit(status);
            } catch (Exception e) {
                this.e = e;
            }
        }

        public Exception getException() {
            return e;
        }
    }

    @Test
    @DisplayName("현재 시간보다 이전 시간의 예약을 시도하면 예외가 발생한다.")
    void createLate() {
        // given
        ReservationRequest request = ReservationFixture.pastRequest();

        // when & then
        assertThatThrownBy(() -> reservationService.createReservedReservation(request, MemberFixture.member1()))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("현재 시각보다 이전의 예약은 불가능합니다.");
    }

    @Test
    @DisplayName("관리자 계정으로 예약한다.")
    void createAdmin() {
        // given
        ReservationAdminRequest adminRequest = ReservationFixture.newAdminRequest();
        Member member = MemberFixture.member1();

        // when
        ReservationResponse result = reservationService.createReservationByAdmin(adminRequest);

        // then
        assertThat(result).isEqualTo(ReservationFixture.newResponse(ReservationStatus.RESERVED));
    }

    @Test
    @DisplayName("예약 대기를 생성한다.")
    void createWaiting() {
        // given
        ReservationRequest request = ReservationFixture.newRequest();
        reservationService.createReservedReservation(request, MemberFixture.member1());

        // when
        reservationService.createWaitingReservation(request, MemberFixture.member2());

        // then
        Reservation waiting = reservationRepository.findById(INITIAL_RESERVATION_SIZE + 2L).get();
        assertThat(waiting.getStatus()).isEqualTo(ReservationStatus.WAITING);
    }

    @Test
    @DisplayName("특정 슬롯에 대해 이미 예약에 성공한 회원은 예약 대기 시 예외가 발생한다.")
    void createWaitingFailAlreadyReserved() {
        // given
        ReservationRequest request = ReservationFixture.newRequest();
        reservationService.createReservedReservation(request, MemberFixture.member1());

        // when & then
        assertThatThrownBy(() -> reservationService.createWaitingReservation(request, MemberFixture.member1()))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(ErrorCode.DUPLICATED_WAITING.getMessage());
    }

    @Test
    @DisplayName("특정 슬롯에 대해 이미 예약 대기에 성공한 회원은 예약 대기 시 예외가 발생한다.")
    void createWaitingFailAlreadyWaiting() {
        // given
        ReservationRequest request = ReservationFixture.newRequest();
        reservationService.createReservedReservation(request, MemberFixture.member1());
        reservationService.createWaitingReservation(request, MemberFixture.member2());

        // when & then
        assertThatThrownBy(() -> reservationService.createWaitingReservation(request, MemberFixture.member2()))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(ErrorCode.DUPLICATED_WAITING.getMessage());
    }

    @Test
    void findAll() {
        // when
        List<ReservationResponse> result = reservationService.findAll();

        // then
        assertThat(result.size()).isEqualTo(INITIAL_RESERVATION_SIZE);
    }

    @Test
    @DisplayName("특정 회원의 모든 예약을 조회한다.")
    void findOfMember() {
        // given
        Member member = MemberFixture.member1();

        // when
        List<ReservationMineResponse> result = reservationService.findOfMember(member);

        // then
        List<Long> reservationIds = result.stream()
                .map(ReservationMineResponse::id)
                .toList();
        assertThat(reservationIds).containsExactly(RESERVATION_1_ID);
    }

    @Test
    @DisplayName("관리자는 모든 예약을 취소할 수 있다.")
    void removeByAdmin() {
        // given
        assertThat(reservationRepository.findById(RESERVATION_2_ID)).isNotEmpty();

        // when
        reservationService.remove(RESERVATION_2_ID, MemberFixture.member1());

        // then
        assertThat(reservationRepository.findById(RESERVATION_2_ID).get().getStatus())
                .isEqualTo(ReservationStatus.CANCEL);
    }

    @Test
    @DisplayName("예약자는 자신의 예약을 취소할 수 있다.")
    void removeByOwner() {
        // given
        assertThat(reservationRepository.findById(RESERVATION_2_ID)).isNotEmpty();

        // when
        reservationService.remove(RESERVATION_2_ID, MemberFixture.member2());

        // then
        assertThat(reservationRepository.findById(RESERVATION_2_ID).get().getStatus())
                .isEqualTo(ReservationStatus.CANCEL);
    }

    @Test
    @DisplayName("관리자 또는 예약자가 아닌 회원이 예약 취소를 시도하면 예외가 발생한다.")
    void removeFail() {
        // given
        assertThat(reservationRepository.findById(RESERVATION_2_ID)).isNotEmpty();

        // when & then
        assertThatThrownBy(() -> reservationService.remove(RESERVATION_2_ID, MemberFixture.member3()))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("예약을 취소할 권한이 없습니다.");
    }
}
