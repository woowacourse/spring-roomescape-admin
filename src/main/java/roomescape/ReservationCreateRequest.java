package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(LocalDate date, String name, LocalTime time) {

    public Reservation toEntity(Long id) {
        return new Reservation(id, this.name(), this.date(), this.time());
    }
}
