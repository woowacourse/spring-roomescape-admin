package roomescape.web.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

public record ReservationRequest(
        LocalDate date,
        String name,
        Long timeId) {
    public Reservation toReservation(Time time) {
        return new Reservation(name, date, time);
    }
}
