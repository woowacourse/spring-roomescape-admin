package roomescape.time.dto;

import java.time.LocalTime;

public record ReservationTimeResponse(
        Long id,
        LocalTime startAt
) {
}
