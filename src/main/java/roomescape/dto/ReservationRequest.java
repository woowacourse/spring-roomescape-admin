package roomescape.dto;

import roomescape.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {

    public Reservation toEntity() {
        return Reservation.of(name, date, time);
    }
}
