package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {
    public Reservation toReservation(Long id) {
        return new Reservation(id, name, date, time);
    }
}
