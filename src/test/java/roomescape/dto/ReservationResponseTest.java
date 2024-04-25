package roomescape.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.model.Reservation;

class ReservationResponseTest {

    @DisplayName("dto 변환")
    @Test
    void fromReservation() {
        final long id = 1L;
        final String name = "레디";
        final String date = "2024-04-18";
        final long timeId = 1L;
        final String startAt = "13:00";

        final TimeResponse timeResponse = new TimeResponse(timeId, startAt);
        final ReservationResponse reservationResponse = new ReservationResponse(id, name, date, timeResponse);

        final Reservation reservation = new Reservation(id, name, date, timeId, startAt);

        assertThat(ReservationResponse.from(reservation)).isEqualTo(reservationResponse);
    }
}
