package roomescape.dto;

import roomescape.Reservation;
import roomescape.ReservationTime;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        ReservationTime time
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
               reservation.getTime());
    }
}
