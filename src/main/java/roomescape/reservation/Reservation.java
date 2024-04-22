package roomescape.reservation;

import java.time.LocalDate;
import roomescape.time.Time;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private Time time;

    public Reservation() {
    }

    public Reservation(final Long id, final String name, final LocalDate date, final Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
}
