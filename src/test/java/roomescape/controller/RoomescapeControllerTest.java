package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static roomescape.test.utility.HttpResponseTestUtility.checkLocationHeader;
import static roomescape.test.utility.HttpResponseTestUtility.checkStatusCode;
import static roomescape.test.utility.ReservationTestUtility.checkReservation;
import static roomescape.test.utility.ReservationTestUtility.checkReservationId;
import static roomescape.test.utility.ReservationsTestUtility.checkDeleteReservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreationRequest;
import roomescape.dto.ReservationCreationResponse;
import roomescape.repository.ReservationRepository;
import roomescape.test.fake.FakeReservationRepository;

class RoomescapeControllerTest {

    private final ReservationRepository reservationRepository = new FakeReservationRepository();
    private final RoomescapeController controller = new RoomescapeController(reservationRepository);

    @DisplayName("저장된 예약들을 조회할 수 있다")
    @Test
    void getReservations() {
        reservationRepository.add("reservation1", LocalDate.now(), LocalTime.now());
        reservationRepository.add("reservation2", LocalDate.now(), LocalTime.now());
        reservationRepository.add("reservation3", LocalDate.now(), LocalTime.now());

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
        Reservation expecteReservation = new Reservation(
                1L, "reservation1", LocalDate.now().plusDays(1), LocalTime.now());
        ReservationCreationRequest input = new ReservationCreationRequest(
                expecteReservation.getName(), expecteReservation.getDate(), expecteReservation.getTime());

        ResponseEntity<ReservationCreationResponse> response = controller.createReservation(input);

        Reservation newReservation = reservationRepository.findAll().getFirst();
        assertAll(
                () -> checkReservation(newReservation, expecteReservation),
                () -> checkStatusCode(response, HttpStatus.CREATED),
                () -> checkLocationHeader(response, "reservations/" + newReservation.getId()),
                () -> checkReservationId(response.getBody().id(), 1L)
        );
    }


    @DisplayName("과거 날짜와 시간으로는 예약을 추가할 수 없다.")
    @Test
    void canNotCreateReservationWithPastDateTime() {
        LocalDateTime past = LocalDateTime.now().minusNanos(1);
        ReservationCreationRequest input = new ReservationCreationRequest(
                "reservation", past.toLocalDate(), past.toLocalTime());

        ResponseEntity<ReservationCreationResponse> response = controller.createReservation(input);

        assertAll(
                () -> assertThat(reservationRepository.findAll()).isEmpty(),
                () -> checkStatusCode(response, HttpStatus.BAD_REQUEST)
        );
    }

    @DisplayName("특정 ID의 예약을 삭제할 수 있다.")
    @Test
    void deleteReservation() {
        reservationRepository.add("reservation1", LocalDate.now(), LocalTime.now());
        reservationRepository.add("reservation2", LocalDate.now(), LocalTime.now());
        reservationRepository.add("reservation3", LocalDate.now(), LocalTime.now());
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