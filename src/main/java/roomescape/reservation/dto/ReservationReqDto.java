package roomescape.reservation.dto;

import roomescape.reservation.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationReqDto(String name, LocalDate date, LocalTime time) {

    public Reservation toEntity() {
        return new Reservation(name, date, time);
    }
}
