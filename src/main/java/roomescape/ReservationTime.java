package roomescape;

import java.time.LocalTime;

public record ReservationTime(
        long id,
        LocalTime startTime
) {
}
