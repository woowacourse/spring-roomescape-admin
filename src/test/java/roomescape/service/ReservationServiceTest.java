package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static roomescape.test.fixture.ReservationTimeFixture.createReservationTime;
import static roomescape.test.utility.ReservationTestUtility.checkDeleteReservation;
import static roomescape.test.utility.ReservationTestUtility.checkReservationFieldWithoutId;
import static roomescape.test.utility.ReservationTestUtility.checkReservationId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreationRequest;
import roomescape.exception.BadRequestException;
import roomescape.exception.NotFoundException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.test.fake.FakeReservationRepository;
import roomescape.test.fake.FakeReservationTimeRepository;
import roomescape.test.fixture.ReservationFixture;

class ReservationServiceTest {

    private final ReservationRepository reservationRepository = new FakeReservationRepository();
    private final ReservationTimeRepository timeRepository = new FakeReservationTimeRepository();
    private final ReservationService reservationService = new ReservationService(reservationRepository, timeRepository);

    @DisplayName("저장된 예약들을 조회할 수 있다")
    @Test
    void getReservations() {
        ReservationTime reservationTime = createReservationTime(1L, LocalTime.now());
        timeRepository.add(reservationTime);
        reservationRepository.add(ReservationFixture.createReservation("reservation1", reservationTime));
        reservationRepository.add(ReservationFixture.createReservation("reservation2", reservationTime));
        reservationRepository.add(ReservationFixture.createReservation("reservation3", reservationTime));

        List<Reservation> allReservations = reservationService.getAllReservations();

        assertThat(allReservations).hasSize(3);
    }

    @DisplayName("예약을 추가할 수 있다.")
    @Test
    void createReservation() {
        ReservationTime reservationTime = addReservationTime(LocalTime.now());
        Reservation expected = ReservationFixture.createReservation("reservation1", reservationTime);
        ReservationCreationRequest request =
                new ReservationCreationRequest(expected.getName(), expected.getDate(), reservationTime.getId());

        long id = reservationService.saveReservation(request);

        Reservation newReservation = reservationRepository.findAll().getFirst();
        assertAll(
                () -> assertThat(id).isEqualTo(1L),
                () -> checkReservationId(newReservation.getId(), 1L),
                () -> checkReservationFieldWithoutId(newReservation, expected)
        );
    }

    @DisplayName("과거 날짜와 시간으로는 예약을 추가할 수 없다.")
    @Test
    void canNotCreateReservationWithPastDateTime() {
        ReservationTime pastTime = addReservationTime(LocalTime.now().minusSeconds(1));
        Reservation expected = ReservationFixture.createReservation("reservation1", LocalDate.now(), pastTime);
        ReservationCreationRequest request =
                new ReservationCreationRequest(expected.getName(), expected.getDate(), pastTime.getId());

        assertThatThrownBy(() -> reservationService.saveReservation(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("[ERROR] 이미 과거의 날짜와 시간입니다.");
    }

    @DisplayName("이미 예약한 날짜와 시간으로는 예약이 불가능하다")
    @Test
    void canNotCreateReservationWithSameDateTime() {
        LocalDate sameDate = LocalDate.now().plusDays(1);
        ReservationTime sameTime = addReservationTime(LocalTime.of(10, 0));
        reservationRepository.add(ReservationFixture.createReservation("reservation1", sameDate, sameTime));
        ReservationCreationRequest request =
                new ReservationCreationRequest("reservation2", sameDate, sameTime.getId());

        assertThatThrownBy(() -> reservationService.saveReservation(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("[ERROR] 이미 예약이 완료된 날짜와 시간입니다.");
    }

    @DisplayName("특정 ID의 예약을 삭제할 수 있다.")
    @Test
    void deleteReservation() {
        ReservationTime reservationTime = addReservationTime(LocalTime.now());
        reservationRepository.add(ReservationFixture.createReservation("reservation1", reservationTime));
        reservationRepository.add(ReservationFixture.createReservation("reservation2", reservationTime));
        reservationRepository.add(ReservationFixture.createReservation("reservation3", reservationTime));
        long deleteReservationId = reservationRepository.findAll().getFirst().getId();

        reservationService.deleteReservation(deleteReservationId);

        List<Reservation> reservations = reservationRepository.findAll();
        checkDeleteReservation(reservations, deleteReservationId);
    }

    @DisplayName("존재하지 않는 예약을 삭제하려고 할 경우 예외 응답을 보낸다.")
    @Test
    void deleteNoneExistentReservation() {
        long noneExistentReservationId = 1L;
        assertThatThrownBy(() -> reservationService.deleteReservation(noneExistentReservationId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("[ERROR] ID에 해당하는 예약이 존재하지 않습니다.");
    }

    private ReservationTime addReservationTime(LocalTime time) {
        ReservationTime reservationTime = createReservationTime(LocalTime.now().minusSeconds(1));
        long timeId = timeRepository.add(reservationTime);
        return timeRepository.findById(timeId).get();
    }
}