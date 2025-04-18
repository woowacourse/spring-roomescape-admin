package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationReq(String name, LocalDate date, LocalTime time) {

    public Reservation toEntity(final long id) {
        return new Reservation(id, name, date, time);
    }
}
