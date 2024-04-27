package roomescape.dto;

import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationRequestTest {
    @Test
    void convert_Request_to_Reservation() {
        ReservationRequest reservationRequest = new ReservationRequest(LocalDate.of(2024, 4, 23)
                , "abc", 1L);
        Reservation reservation = reservationRequest.toReservation(1L, new Time(1L, LocalTime.of(15, 0)));
        Reservation target = new Reservation(1L, "abc", LocalDate.of(2024, 4, 23)
                , new Time(1L, LocalTime.of(15, 0)));

        assertThat(reservation).usingRecursiveComparison().isEqualTo(target);
    }
}
