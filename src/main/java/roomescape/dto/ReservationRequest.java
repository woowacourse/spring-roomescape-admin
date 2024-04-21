package roomescape.dto;

import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

public record ReservationRequest(String name, String date, long timeId) {

    public Reservation toEntity(long id, ReservationTime reservationTime) {
        return new Reservation(id, name, date, reservationTime);
    }
}
