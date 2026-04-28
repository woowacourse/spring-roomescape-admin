package roomescape.domain.reservations.presentation.dto;

import roomescape.domain.reservations.entity.Reservation;

public record ReservationCreateResponse(
        Long id,
        String name,
        String date,
        String time
) {
    public static ReservationCreateResponse from(Reservation reservation) {
        return new ReservationCreateResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().toString(),
                reservation.getTime().toString()
        );
    }
}
