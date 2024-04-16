package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(String name, LocalDate date, LocalTime time) {

    public Reservation toEntity(int id) {
        return new Reservation(id, name, date, time);
    }
}
