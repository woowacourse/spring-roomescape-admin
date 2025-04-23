package roomescape.reservation.model;

import java.time.LocalTime;

public record ReservationTimeDetails(
        LocalTime startAt
) {
}
