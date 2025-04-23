package roomescape;

import java.time.LocalTime;

public record ReservationTimeResponseDto(Long id, LocalTime start_at) {
}
