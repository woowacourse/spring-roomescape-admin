package roomescape.reservation.dto;

import java.util.List;

public record ReservationsResponseDto(
        List<ReservationResponseDto> reservations
) {
}
