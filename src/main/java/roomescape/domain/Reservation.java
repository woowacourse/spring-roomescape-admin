package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(String name, LocalDate date, LocalTime time) {
        this(0L, name, date, new ReservationTime(time));
    }

    public Reservation(String name, LocalDate date, ReservationTime time) {
        this(0L, name, date, time);
    }

    public Reservation(long id, String name, LocalDate date, LocalTime time) {
        this(id, name, date, new ReservationTime(time));
    }

    public Reservation(long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public boolean hasId(long id) {
        return this.id == id;
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

    public ReservationTime getTime() {
        return time;
    }
}
