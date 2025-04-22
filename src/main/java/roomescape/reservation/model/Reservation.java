package roomescape.reservation.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {

    private final String name;
    private final LocalDateTime datetime;

    public Reservation(final String name, final LocalDateTime datetime) {
        this.name = name;
        this.datetime = datetime;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return datetime.toLocalDate();
    }

    public LocalTime getTime() {
        return datetime.toLocalTime();
    }
}
