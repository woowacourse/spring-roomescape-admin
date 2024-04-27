package roomescape.dto.reservation;

import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.Time;

import java.time.LocalDate;

public record ReservationRequest(String name, LocalDate date, Long timeId) {

    public Reservation toReservation(Time time) {
        return new Reservation(this.name, this.date, time);
    }
}
