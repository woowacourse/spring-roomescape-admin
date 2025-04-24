package roomescape.service.response;

import java.time.LocalTime;

public record ReservationTimeResponse(
        Long id,
        LocalTime startAt
) {
}
