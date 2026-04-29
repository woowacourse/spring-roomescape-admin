package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;

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
}
