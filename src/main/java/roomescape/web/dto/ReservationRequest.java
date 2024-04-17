package roomescape.web.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.db.domain.Reservation;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {

    public Reservation toReservation(final long id) {
       return new Reservation(id, name, date, time);
    }
}
