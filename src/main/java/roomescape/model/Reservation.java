package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(long id, String name, LocalDate date, LocalTime time) {

    public Reservation copyWithId(long id) {
        return new Reservation(id, name, date, time);
    }
}
