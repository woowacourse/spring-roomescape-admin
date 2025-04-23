package roomescape.reservation.model;

import java.time.LocalDate;

public record ReservationDetails(
        String name,
        LocalDate date,
        ReservationTime time
) {
}
