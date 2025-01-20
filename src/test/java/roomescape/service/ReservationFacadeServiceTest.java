package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationStatus;
import roomescape.fixture.MemberFixture;
import roomescape.fixture.ReservationFixture;
import roomescape.repository.ReservationRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationFacadeServiceTest {

    @Autowired
    private ReservationFacadeService reservationFacadeService;
    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("이미 등록된 슬롯에 예약을 시도하면 예약에 실패하고, 예약 대기로 재요청된다.")
    void createReservation() {
        // given
        reservationFacadeService.createReservation(ReservationFixture.newRequest(), MemberFixture.member1());

        // when
        reservationFacadeService.createReservation(ReservationFixture.newRequest(), MemberFixture.member2());

        // then
        Reservation waiting = reservationRepository.findById(ReservationFixture.INITIAL_RESERVATION_SIZE + 2L)
                .get();
        assertThat(waiting.getStatus()).isEqualTo(ReservationStatus.WAITING);
    }
}
