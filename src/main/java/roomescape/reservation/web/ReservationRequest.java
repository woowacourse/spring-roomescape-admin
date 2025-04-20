package roomescape.reservation.web;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservation.Reservation;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {
    public Reservation toReservation() {
        return new Reservation(null, name, date, time);
    }
}
