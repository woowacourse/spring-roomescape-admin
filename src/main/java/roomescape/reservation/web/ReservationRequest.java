package roomescape.reservation.web;

import java.time.LocalDate;
import roomescape.reservation.Reservation;
import roomescape.time.ReservationTime;

public record ReservationRequest(String name, LocalDate date, Long timeId) {
    public Reservation toReservation(ReservationTime time) {
        return new Reservation(null, name, date, time);
    }
}
