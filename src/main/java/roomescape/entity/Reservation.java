package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(Long id, String name, LocalDate date, LocalTime time) {

    public Reservation assignId(Long id) {
        return new Reservation(id, name, date, time);
    }
}
