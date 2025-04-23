package roomescape.controller.reservation.request;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.model.Reservation;

public record ReservationRequest(
        String name,
        LocalDate date,
        LocalTime time) {

    public Reservation toReservation() {
        return Reservation.from(this.name, this.date, this.time);
    }

}
