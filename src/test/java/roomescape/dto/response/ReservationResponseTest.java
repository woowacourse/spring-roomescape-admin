package roomescape.dto.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

class ReservationResponseTest {

    @Test
    @DisplayName("Reservation 객체로 ReservationResponse를 만든다.")
    void createReservationResponse() {
        Reservation reservation = new Reservation(
                1L,
                "brown",
                LocalDate.of(2024, 4, 1),
                ReservationTime.from(1L)
        );

        ReservationResponse expected = new ReservationResponse(
                1L,
                "brown",
                "2024-04-01",
                ReservationTimeResponse.from(ReservationTime.from(1L))
        );

        ReservationResponse reservationResponse = ReservationResponse.from(reservation);

        assertThat(reservationResponse).isEqualTo(expected);
    }
}
