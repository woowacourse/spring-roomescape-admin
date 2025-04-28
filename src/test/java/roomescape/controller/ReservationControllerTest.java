package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.TestReservationServiceImpl;

class ReservationControllerTest {

    @Test
    @DisplayName("모든 예약 목록을 조회한다")
    void read_all_reservations() {
        // given
        ReservationController reservationController = new ReservationController(new TestReservationServiceImpl());

        // when
        ResponseEntity<List<ReservationResponse>> response = reservationController.readReservations();
        List<ReservationResponse> reservationResponse = response.getBody();
        int statusCode = response.getStatusCode()
                .value();

        // then
        assertAll(
                () -> assertThat(reservationResponse).isEmpty(),
                () -> assertThat(statusCode).isEqualTo(200)
        );
    }

    @Test
    @DisplayName("예약을 생성한다")
    void create_reservation() {
        // given
        String date = "2025-04-21";
        ReservationRequest reservationRequest = new ReservationRequest("kim", date, 1L);
        ReservationController reservationController = new ReservationController(new TestReservationServiceImpl());

        // when
        ResponseEntity<ReservationResponse> response = reservationController.createReservation(reservationRequest);
        int statusCode = response.getStatusCode()
                .value();

        // then
        assertThat(statusCode).isEqualTo(200);
    }

    @Test
    @DisplayName("id에 해당하는 예약을 삭제한다")
    void delete_reservation() {
        // given
        String date = "2025-04-21";
        ReservationRequest reservationRequest = new ReservationRequest("kim", date, 1L);
        ReservationController reservationController = new ReservationController(new TestReservationServiceImpl());
        ResponseEntity<ReservationResponse> createdResponse = reservationController.createReservation(
                reservationRequest);
        Long createdId = createdResponse.getBody()
                .id();

        // when
        ResponseEntity<Void> response = reservationController.deleteReservation(createdId);
        int statusCode = response.getStatusCode()
                .value();

        // then
        assertThat(statusCode).isEqualTo(200);
    }

    @Test
    @DisplayName("존재하지 않는 예약 삭제 시 400 반환")
    void delete_reservation_when_not_exist_id() {
        // given
        ReservationController reservationController = new ReservationController(new TestReservationServiceImpl());

        // when
        ResponseEntity<Void> response = reservationController.deleteReservation(2L);
        int statusCode = response.getStatusCode()
                .value();

        // then
        assertThat(statusCode).isEqualTo(400);
    }
}
