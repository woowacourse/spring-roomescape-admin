package roomescape.dto;

import java.time.LocalTime;

public record ReservationTimeRequestDto(Long id, LocalTime startAt) {
}
