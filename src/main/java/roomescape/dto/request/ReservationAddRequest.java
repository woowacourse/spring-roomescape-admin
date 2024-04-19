package roomescape.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationAddRequest(String name, LocalDate date, LocalTime time) {

    public Reservation toReservation() {
        return new Reservation(name, date, time);
    }
}
