package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static roomescape.test.fixture.ReservationTimeFixture.createReservationTime;
import static roomescape.test.utility.HttpResponseTestUtility.checkLocationHeader;
import static roomescape.test.utility.HttpResponseTestUtility.checkStatusCode;
import static roomescape.test.utility.ReservationTestUtility.checkDeleteReservation;
import static roomescape.test.utility.ReservationTestUtility.checkReservationFieldWithoutId;
import static roomescape.test.utility.ReservationTestUtility.checkReservationId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreationRequest;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.ReservationService;
import roomescape.test.fake.FakeReservationRepository;
import roomescape.test.fake.FakeReservationTimeRepository;
import roomescape.test.fixture.ReservationFixture;

class ReservationControllerTest {

    private final ReservationRepository reservationRepository = new FakeReservationRepository();
    private final ReservationTimeRepository timeRepository = new FakeReservationTimeRepository();
    private final ReservationService reservationService = new ReservationService(reservationRepository, timeRepository);
    private final ReservationController controller = new ReservationController(reservationService);

    @DisplayName("저장된 예약들을 조회할 수 있다")
    @Test
    void getReservations() {
        ReservationTime reservationTime = createReservationTime(1L, LocalTime.now());
        timeRepository.add(reservationTime);
        reservationRepository.add(ReservationFixture.createReservation("reservation1", reservationTime));
        reservationRepository.add(ReservationFixture.createReservation("reservation2", reservationTime));
        reservationRepository.add(ReservationFixture.createReservation("reservation3", reservationTime));

        ResponseEntity<List<Reservation>> response = controller.getReservations();
        List<Reservation> actualReservations = response.getBody();

        assertAll(
                () -> assertThat(actualReservations).hasSize(3),
                () -> checkStatusCode(response, HttpStatus.OK)
        );
    }

    @DisplayName("예약을 추가할 수 있다.")
    @Test
    void createReservation() {
        ReservationTime reservationTime = createReservationTime(1L, LocalTime.now());
        timeRepository.add(reservationTime);
        Reservation expected = ReservationFixture.createReservation("reservation1", reservationTime);
        ReservationCreationRequest request = new ReservationCreationRequest(expected.getName(), expected.getDate(), 1L);

        ResponseEntity<Reservation> response = controller.createReservation(request);

        Reservation newReservation = reservationRepository.findAll().getFirst();
        assertAll(
                () -> checkReservationId(newReservation.getId(), 1L),
                () -> checkReservationFieldWithoutId(newReservation, expected),
                () -> checkStatusCode(response, HttpStatus.CREATED),
                () -> checkLocationHeader(response, "reservations/" + newReservation.getId()),
                () -> checkReservationId(response.getBody().getId(), 1L),
                () -> checkReservationFieldWithoutId(response.getBody(), expected)
        );
    }


    @DisplayName("과거 날짜와 시간으로는 예약을 추가할 수 없다.")
    @Test
    void canNotCreateReservationWithPastDateTime() {
        ReservationTime pastTime = createReservationTime(1L, LocalTime.now().minusSeconds(1));
        timeRepository.add(pastTime);
        ReservationCreationRequest request =
                new ReservationCreationRequest("reservation", LocalDate.now(), pastTime.getId());

        ResponseEntity<Reservation> response = controller.createReservation(request);

        assertAll(
                () -> assertThat(reservationRepository.findAll()).isEmpty(),
                () -> checkStatusCode(response, HttpStatus.BAD_REQUEST)
        );
    }

    @DisplayName("이미 예약한 날짜와 시간으로는 예약이 불가능하다")
    @Test
    void canNotCreateReservationWithSameDateTime() {
        LocalDate sameDate = LocalDate.now().plusDays(1);
        ReservationTime sameReservationTime = createReservationTime(1L, LocalTime.of(10, 0));
        timeRepository.add(sameReservationTime);
        reservationRepository.add(ReservationFixture.createReservation("reservation1", sameDate, sameReservationTime));

        ReservationCreationRequest request =
                new ReservationCreationRequest("reservation2", sameDate, sameReservationTime.getId());
        ResponseEntity<Reservation> response = controller.createReservation(request);

        assertAll(
                () -> assertThat(reservationRepository.findAll()).hasSize(1),
                () -> checkStatusCode(response, HttpStatus.BAD_REQUEST)
        );
    }

    @DisplayName("특정 ID의 예약을 삭제할 수 있다.")
    @Test
    void deleteReservation() {
        ReservationTime reservationTime = createReservationTime(1L, LocalTime.now());
        timeRepository.add(reservationTime);
        reservationRepository.add(ReservationFixture.createReservation("reservation1", reservationTime));
        reservationRepository.add(ReservationFixture.createReservation("reservation2", reservationTime));
        reservationRepository.add(ReservationFixture.createReservation("reservation3", reservationTime));
        long deleteReservationId = reservationRepository.findAll().getFirst().getId();

        ResponseEntity<Void> response = controller.deleteReservation(deleteReservationId);

        List<Reservation> reservations = reservationRepository.findAll();
        assertAll(
                () -> checkDeleteReservation(reservations, deleteReservationId),
                () -> checkStatusCode(response, HttpStatus.OK)
        );
    }

    @DisplayName("존재하지 않는 예약을 삭제하려고 할 경우 예외 응답을 보낸다.")
    @Test
    void deleteNoneExistentReservation() {
        long noneExistentReservationId = 1L;

        ResponseEntity<Void> response = controller.deleteReservation(noneExistentReservationId);
        checkStatusCode(response, HttpStatus.NOT_FOUND);
    }
}