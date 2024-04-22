package roomescape.dto.reservation.response;

import java.util.List;

public record ReservationsResponseDto(
        List<ReservationResponseDto> reservations
) {
}
