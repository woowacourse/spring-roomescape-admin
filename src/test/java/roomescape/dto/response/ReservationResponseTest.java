package roomescape.dto.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

class ReservationResponseTest {

    @Test
    @DisplayName("id와 Reservation 객체로 ReservationResponse를 만든다.")
    void createReservationResponse() {
        Long id = 1L;
        Reservation reservation = new Reservation("brown",
                LocalDate.of(2024, 4, 1),
                LocalTime.of(15, 32));
        ReservationResponse expected = new ReservationResponse(
                1L,
                "brown",
                LocalDate.of(2024, 4, 1),
                LocalTime.of(15, 32));

        ReservationResponse reservationResponse = ReservationResponse.of(id, reservation);

        assertThat(reservationResponse).isEqualTo(expected);
    }
}