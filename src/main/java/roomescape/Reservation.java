package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final Name name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(String name, LocalDate date, LocalTime time) {
        this.id = null;
        this.name = new Name(name);
        this.date = date;
        this.time = time;
    }

    public Reservation(Long id, Reservation reservation) {
        this.id = id;
        this.name = reservation.name;
        this.date = reservation.date;
        this.time = reservation.time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
