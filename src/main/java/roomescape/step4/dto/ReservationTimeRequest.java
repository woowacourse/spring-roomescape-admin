package roomescape.step4.dto;

import java.time.LocalTime;

public record ReservationTimeRequest(
        LocalTime startAt
) {
}
