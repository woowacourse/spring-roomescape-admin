package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        ReservationTime time
) {
    public static ReservationResponse of(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
