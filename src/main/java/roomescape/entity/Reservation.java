package roomescape.entity;

import java.time.LocalDate;

public record Reservation(Long id, String name, LocalDate date, ReservationTime time) {

    public Reservation assignId(Long id) {
        return new Reservation(id, name, date, time);
    }

    public Reservation assignTime(ReservationTime time) {
        return new Reservation(id, name, date, time);
    }
}
