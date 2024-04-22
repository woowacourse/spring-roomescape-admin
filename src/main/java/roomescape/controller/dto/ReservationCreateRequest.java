package roomescape.controller.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationCreateRequest(String name, String date, Long timeId) {

    public Reservation toReservation(ReservationTime reservationTime) {
        return new Reservation(name, date, reservationTime);
    }
}
