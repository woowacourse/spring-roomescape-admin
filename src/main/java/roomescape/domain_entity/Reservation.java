package roomescape.domain_entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Id id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation() {
    }

    public Reservation(String name, LocalDate date, LocalTime time) {
        this.id = new Id();
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public boolean isSameId(Id id) {
        return this.id.equals(id);
    }

    public Id getId() {
        return id;
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
