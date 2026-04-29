package roomescape.reservation.dto;

import roomescape.reservation.entity.Reservation;
import roomescape.time.entity.ReservationTime;

public record ReservationResponse(
        long id,
        String name,
        String date,
        ReservationTime reservationTime
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
