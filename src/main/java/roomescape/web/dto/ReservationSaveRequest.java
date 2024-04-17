package roomescape.web.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.dao.Reservation;

public record ReservationSaveRequest(LocalDate date, String name, LocalTime time) {

    public Reservation toEntity(Long id) {
        return new Reservation(id, this.name(), this.date(), this.time());
    }
}
