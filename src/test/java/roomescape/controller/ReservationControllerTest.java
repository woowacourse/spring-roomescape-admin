package roomescape.controller;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import roomescape.dto.ReservationResponse;

class ReservationControllerTest {
    @Test
    @DisplayName("예약자 정보를 잘 불러오는지 확인한다.")
    void findAllReservations() {
        ReservationController reservationController = new ReservationController();
        ResponseEntity<List<ReservationResponse>> allReservations = reservationController.findAllReservations();

        assertAll(
                () -> Assertions.assertThat(allReservations.getBody())
                        .isEmpty(),
                () -> Assertions.assertThat(allReservations.getStatusCode())
                        .isEqualTo(HttpStatusCode.valueOf(200))
        );
    }
}
