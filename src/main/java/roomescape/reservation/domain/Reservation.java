package roomescape.reservation.domain;

import java.time.LocalDate;
import roomescape.time.domain.Time;

public class Reservation {
    private final Long id;
    private final Name name;
    private final LocalDate date;
    private final Time time;

    public Reservation(final Long id, final Name name, final LocalDate date, final Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public LocalDate getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
}
