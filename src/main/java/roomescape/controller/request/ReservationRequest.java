package roomescape.controller.request;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.Reservation;

public record ReservationRequest(
        String name,
        LocalDate date,
        LocalTime time) {

    public Reservation toReservation(final Long reservationIndex) {
        return new Reservation(reservationIndex, this.name, this.date, this.time);
    }

}
