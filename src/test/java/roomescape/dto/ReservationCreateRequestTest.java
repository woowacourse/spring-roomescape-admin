package roomescape.dto;

import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationCreateRequestTest {
    @Test
    void convert_Request_to_Reservation() {
        ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(LocalDate.of(2024, 4, 23)
                , "abc", 1L);
        Reservation reservation = reservationCreateRequest.toReservation(1L, new ReservationTime(1L, LocalTime.of(15, 0)));
        Reservation target = new Reservation(1L, "abc", LocalDate.of(2024, 4, 23)
                , new ReservationTime(1L, LocalTime.of(15, 0)));

        assertThat(reservation).usingRecursiveComparison().isEqualTo(target);
    }
}
