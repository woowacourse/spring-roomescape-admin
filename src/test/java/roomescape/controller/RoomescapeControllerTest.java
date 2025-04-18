package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static roomescape.test.utility.HttpResponseTestUtility.checkLocationHeader;
import static roomescape.test.utility.HttpResponseTestUtility.checkStatusCode;
import static roomescape.test.utility.ReservationTestUtility.checkReservation;
import static roomescape.test.utility.ReservationsTestUtility.checkDeleteReservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;
import roomescape.vo.ReservationCreateVo;

class RoomescapeControllerTest {

    Reservations reservations = new Reservations();
    RoomescapeController controller = new RoomescapeController(reservations);

    @DisplayName("저장된 예약들을 조회할 수 있다")
    @Test
    void getReservations() {
        reservations.add("reservation1", LocalDate.now(), LocalTime.now());
        reservations.add("reservation2", LocalDate.now(), LocalTime.now());
        reservations.add("reservation3", LocalDate.now(), LocalTime.now());

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
        Reservation expecteReservation = new Reservation(1L, "reservation1", LocalDate.now(), LocalTime.now());
        ReservationCreateVo input = new ReservationCreateVo(
                expecteReservation.getName(), expecteReservation.getDate(), expecteReservation.getTime());

        ResponseEntity<Reservation> response = controller.createReservation(input);

        Reservation newReservation = reservations.getReservations().getFirst();
        assertAll(
                () -> checkReservation(newReservation, expecteReservation),
                () -> checkStatusCode(response, HttpStatus.CREATED),
                () -> checkLocationHeader(response, "reservations/" + newReservation.getId()),
                () -> checkReservation(response.getBody(), expecteReservation)
        );
    }

    @DisplayName("특정 ID의 예약을 삭제할 수 있다.")
    @Test
    void deleteReservation() {
        reservations.add("reservation1", LocalDate.now(), LocalTime.now());
        reservations.add("reservation2", LocalDate.now(), LocalTime.now());
        reservations.add("reservation3", LocalDate.now(), LocalTime.now());
        long deleteReservationId = reservations.getReservations().getFirst().getId();

        ResponseEntity<Void> response = controller.deleteReservation(deleteReservationId);

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