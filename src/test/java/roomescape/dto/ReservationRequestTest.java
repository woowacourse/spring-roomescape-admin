package roomescape.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.model.Reservation;

class ReservationRequestTest {

    @DisplayName("dto 변환")
    @Test
    void toReservation() {
        final long id = 1L;
        final String name = "레디";
        final String date = "2024-04-18";
        final String time = "13:00";
        final ReservationRequest reservationRequest = new ReservationRequest(name, date, time);

        final Reservation reservation = new Reservation(id, name, date, time);

        assertThat(reservationRequest.toReservation(id)).isEqualTo(reservation);
    }
}
