package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

class ReservationControllerTest {
    ReservationController reservationController;

    @BeforeEach
    void setUp() {
        reservationController = new ReservationController();
    }

    @Test
    @DisplayName("예약을 생성한다.")
    void makeReservation() {
        ReservationRequest request = new ReservationRequest(
                "브라운",
                "2026-04-29"
                , "10:30"
        );

        ReservationResponse response = reservationController.create(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("브라운");
        assertThat(response.date()).isEqualTo("2026-04-29");
        assertThat(response.time()).isEqualTo("10:30");
    }

    @Test
    @DisplayName("아무런 예약이 없는 상태에서 예약을 조회한다.")
    void findAllReservations_Before_Create() {
        List<ReservationResponse> reservations = reservationController.findAll();

        assertThat(reservations).isEmpty();
    }

    @Test
    @DisplayName("예약이 생성된 상태에서 예약을 조회한다.")
    void findAllReservations_After_Create() {
        ReservationRequest request1 = new ReservationRequest("브라운", "2026-04-29", "10:30");
        ReservationRequest request2 = new ReservationRequest("리사", "2026-04-30", "10:40");

        reservationController.create(request1);
        reservationController.create(request2);

        List<ReservationResponse> reservations = reservationController.findAll();

        assertThat(reservations).hasSize(2);
        assertThat(reservations.get(0).id()).isEqualTo(1L);
        assertThat(reservations.get(0).name()).isEqualTo("브라운");
        assertThat(reservations.get(0).date()).isEqualTo("2026-04-29");
        assertThat(reservations.get(0).time()).isEqualTo("10:30");

        assertThat(reservations.get(1).id()).isEqualTo(2L);
        assertThat(reservations.get(1).name()).isEqualTo("리사");
        assertThat(reservations.get(1).date()).isEqualTo("2026-04-30");
        assertThat(reservations.get(1).time()).isEqualTo("10:40");
    }
}
