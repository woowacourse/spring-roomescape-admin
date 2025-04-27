package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

class ReservationControllerTest {

    private final ReservationDAO testReservationDAO = new ReservationDAO() {
        @Override
        public List<Reservation> findAllReservation() {
            return List.of();
        }

        @Override
        public Long insertReservation(Reservation reservation) {
            return 1L;
        }

        @Override
        public int deleteReservationById(Long id) {
            if (id == 1L) {
                return 1;
            }
            return 0;
        }
    };

    @Test
    @DisplayName("모든 예약 목록을 조회한다")
    void read_all_reservations() {
        // given
        ReservationController reservationController = new ReservationController(testReservationDAO);

        // when
        ResponseEntity<List<Reservation>> response = reservationController.readReservations();
        List<Reservation> reservations = response.getBody();
        int statusCode = response.getStatusCode()
                .value();

        // then
        assertAll(
                () -> assertThat(reservations).isEmpty(),
                () -> assertThat(statusCode).isEqualTo(200)
        );
    }

    @Test
    @DisplayName("예약을 생성한다")
    void create_reservation() {
        // given
        String date = "2025-04-21";
        ReservationRequest reservationRequest = new ReservationRequest("kim", date, 1L);
        ReservationController reservationController = new ReservationController(testReservationDAO);

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
        ReservationController reservationController = new ReservationController(testReservationDAO);
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
        ReservationController reservationController = new ReservationController(testReservationDAO);

        // when
        ResponseEntity<Void> response = reservationController.deleteReservation(2L);
        int statusCode = response.getStatusCode()
                .value();

        // then
        assertThat(statusCode).isEqualTo(400);
    }
}
