package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationSaveRequest(
        String name,
        LocalDate date,
        LocalTime time) {

    public Reservation toModel() {
        return new Reservation(name, date, time);
    }
}
