package roomescape.model;

import java.time.LocalDate;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    private Reservation(final Long id, final String name, final LocalDate date, final ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation from(final Long id, final String name, final LocalDate date, final ReservationTime time) {
        return new Reservation(id, name, date, time);
    }

    public static Reservation from(final String name, final LocalDate date, final ReservationTime time) {
        return new Reservation(null, name, date, time);
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

    public ReservationTime getTime() {
        return time;
    }
}
