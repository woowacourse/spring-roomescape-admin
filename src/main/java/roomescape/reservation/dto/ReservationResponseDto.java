package roomescape.reservation.dto;

import roomescape.time.dto.ReservationTimeResponseDto;

public record ReservationResponseDto(
        Long id,
        String name,
        String date,
        ReservationTimeResponseDto time
) {
}
