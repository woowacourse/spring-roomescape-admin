package roomescape.reservation.dto.response;

import java.util.List;

public record ReservationsResponseDto(
        List<ReservationResponseDto> reservations
) {
}
