package roomescape.step3;

import java.time.LocalTime;

public record ReservationTimeRequest(
        LocalTime startAt
) {
}
