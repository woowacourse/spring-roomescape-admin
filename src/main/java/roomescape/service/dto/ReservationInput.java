package roomescape.service.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationInput(String name, String date, Long timeId) {

    public Reservation toReservation(ReservationTime time) {
        return new Reservation(null, name, date, time);
    }
}
