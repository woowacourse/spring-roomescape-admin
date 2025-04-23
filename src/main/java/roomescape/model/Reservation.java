package roomescape.model;

import java.time.LocalDate;

public class Reservation {

    private final String name;
    private final LocalDate date;
    private final Time time;

    public Reservation(String name, LocalDate date, Time time) {
        this.name = name;
        this.date = date;
        this.time = time;
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
