package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {
    public Reservation toReservation(long id) {
        return new Reservation(id, name, date, time);
    }
}
