package roomescape.dto.time;

import java.time.LocalTime;

public record ReservationTimeCreateRequest(
    LocalTime startAt
) {
}
