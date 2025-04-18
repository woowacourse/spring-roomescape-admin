package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    private Reservation(final long id, final String name, final LocalDate date, final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final String name, final LocalDate date, final LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation toEntity(long id) {
        return new Reservation(id, name, date, time);
    }

    public boolean isDuplicateReservation(Reservation reservation) {
        return this.date.equals(reservation.date) && this.time.equals(reservation.time);
    }

    public long getId() {
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
