package roomescape.request;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalDate;

public record ReservationRequest(String name, LocalDate date, Long timeId) {
    public Reservation toReservation(ReservationTime time) {
        return new Reservation(0, name, date, time);
    }
}
