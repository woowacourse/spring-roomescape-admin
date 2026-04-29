package roomescape.time.dto;

import java.time.LocalTime;

public record ReservationTimeRequestDto(
        LocalTime startAt
) {
}
