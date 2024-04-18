package roomescape.controller.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(String name, LocalDate date, LocalTime time) {

    public Reservation toReservation(int id) {
        return new Reservation(id, name, date, time);
    }
}
