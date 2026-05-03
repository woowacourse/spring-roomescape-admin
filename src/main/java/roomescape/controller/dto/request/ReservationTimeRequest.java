package roomescape.controller.dto.request;

import java.time.LocalTime;

public record ReservationTimeRequest(
        LocalTime startAt
) {
}
