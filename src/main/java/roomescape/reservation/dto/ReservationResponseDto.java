package roomescape.reservation.dto;

import roomescape.time.entity.Time;

public record ReservationResponseDto(
        long id,
        String name,
        String date,
        Time time
) {
}
