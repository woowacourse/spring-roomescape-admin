package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Id id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation() {
    }

    public Reservation(Id id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public boolean isSameId(Id id) {
        return this.id.equals(id);
    }

    public long getId() {
        return id.getValue();
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
