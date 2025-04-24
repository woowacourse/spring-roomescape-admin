package roomescape.reservation.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import roomescape.reservationtime.domain.ReservationTime;

class ReservationTest {
    @Test
    void startAt_shouldBeThrownIfTimeIsBeforeNow() {
        assertThatThrownBy(() -> new Reservation(
                "대니",
                LocalDate.now(),
                new ReservationTime(LocalTime.now().minusHours(1)))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}