package roomescape.controller.dto;

import java.util.List;
import roomescape.domain.Reservation;

public record ReservationsResponseDto(
        List<ReservationResponseDto> reservations
) {
    public static ReservationsResponseDto from(List<Reservation> reservations) {
        return new ReservationsResponseDto(reservations
                .stream()
                .map(ReservationResponseDto::from)
                .toList());
    }
}
