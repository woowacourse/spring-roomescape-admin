package roomescape.usecase;

import java.time.LocalTime;

public record ReservationTimeResponseDto(Long id, LocalTime startAt) {
}
