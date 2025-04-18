package roomescape;

import java.time.LocalTime;

public record ReservationTime(
        Long id,
        LocalTime startTime
) {
}
