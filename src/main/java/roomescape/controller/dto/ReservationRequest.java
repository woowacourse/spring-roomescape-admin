package roomescape.controller.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationRequest(String name, String date, Long timeId) {

    public Reservation toInstance(ReservationTime reservationTime) {
        return new Reservation(name, date, reservationTime);
    }
}
