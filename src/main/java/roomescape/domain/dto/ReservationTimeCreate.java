package roomescape.domain.dto;

import java.time.LocalTime;

public record ReservationTimeCreate(
        LocalTime startAt
) {
}
