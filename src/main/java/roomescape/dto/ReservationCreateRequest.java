package roomescape.dto;

import roomescape.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(
        String name,
        LocalDate date,
        LocalTime time
) {
    public Reservation toEntity() {
        return new Reservation(null, name, date, time);
    }
}
