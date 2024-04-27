package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;

public record ReservationRequest(LocalDate date, String name, long timeId) {
    public Reservation toReservation(long id, Time time) {
        return new Reservation(id, name, date, time);
    }
}
