package roomescape.dto;

import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationResponseTest {
    @Test
    void convert_Response_to_Reservation() {
        ReservationResponse response = new ReservationResponse(1L, "abc", LocalDate.of(2024, 4, 23)
                , 1L, LocalTime.of(13, 45));
        Reservation target = new Reservation(1L, "abc", LocalDate.of(2024, 4, 23)
                , new Time(1L, LocalTime.of(13, 45)));
        Reservation reservation = response.toReservation();

        assertThat(reservation).usingRecursiveComparison().isEqualTo(target);
    }
}
