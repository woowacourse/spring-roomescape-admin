package roomescape.domain.reservations.presentation.dto;

import roomescape.domain.reservations.entity.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        String time
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().toString(),
                reservation.getTime().toString()
        );
    }
}
