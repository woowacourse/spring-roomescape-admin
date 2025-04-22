package roomescape.reservation_time.ui.dto;

import java.time.LocalTime;

public record ReservationTimeResponseDto(Long id,
                                         LocalTime startAt) {
}
