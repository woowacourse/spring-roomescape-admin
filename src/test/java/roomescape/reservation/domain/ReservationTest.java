package roomescape.reservation.domain;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    void isSameId() {
        Long sameId = 1L;
        Reservation reservation = new Reservation(
                sameId,
                "아서",
                LocalDate.of(2024, 4, 18),
                null,
                LocalTime.of(12, 30));
        assertTrue(reservation.isSameId(sameId));
    }
}
