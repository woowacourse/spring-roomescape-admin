package roomescape.reservation.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDetails(
        String name,
        LocalDate date,
        LocalTime time
) {
}
