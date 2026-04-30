package roomescape.domain.reservations.presentation.dto;

import roomescape.domain.reservations.entity.Reservation;
import roomescape.domain.reservations.entity.ReservationTime;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        ReservationTimeResponse time
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().toString(),
                ReservationTimeResponse.from(reservation.getTime())
        );
    }
}
