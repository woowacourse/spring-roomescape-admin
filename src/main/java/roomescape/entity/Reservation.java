package roomescape.entity;

import java.time.LocalDate;

public record Reservation(Long id, String name, LocalDate date, ReservationTime time) {

    public Reservation assign(Long id, ReservationTime time) {
        return new Reservation(id, name, date, time);
    }
}
