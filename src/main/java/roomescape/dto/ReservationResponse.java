package roomescape.dto;

import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

public record ReservationResponse(Long id, String name, String date, ReservationTime time) {

    public ReservationResponse(Reservation reservation) {
        this(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
