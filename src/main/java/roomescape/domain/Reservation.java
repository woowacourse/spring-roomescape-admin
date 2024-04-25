package roomescape.domain;

import java.time.LocalDate;

public class Reservation {
    private final Long id;
    private final Name name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = new Name(name);
        this.date = date;
        this.time = time;
    }

    public Reservation(Long id, String name, String date, ReservationTime time) {
        this(id, name, LocalDate.parse(date), time);
    }

    public boolean hasId(Long id) {
        return this.id.equals(id);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
