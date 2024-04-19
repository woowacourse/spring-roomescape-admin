package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {

    public Reservation toEntity() {
        return new Reservation(name, date, time);
    }
}
