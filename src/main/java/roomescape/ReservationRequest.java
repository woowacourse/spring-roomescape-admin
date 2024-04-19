package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {

    public Reservation toReservation() {
        return new Reservation(name, date, time);
    }
}
