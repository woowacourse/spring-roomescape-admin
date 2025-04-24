package roomescape.reservation.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import roomescape.reservationtime.domain.ReservationTime;

class ReservationTest {
    @Test
    void createReservation_shouldThrowException_whenTimeIsBeforeNow() {
        assertThatThrownBy(() -> new Reservation(
                "대니",
                LocalDate.now().minusDays(1),
                new ReservationTime(LocalTime.now()))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}