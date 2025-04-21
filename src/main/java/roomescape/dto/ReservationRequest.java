package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.model.Id;
import roomescape.model.Reservation;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {

    public Reservation toReservation(final Id id) {
        return new Reservation(id.getIdValue(), name, date, time);
    }
}
