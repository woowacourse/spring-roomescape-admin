package roomescape.domain;

import java.time.LocalDate;

public class Reservation {
    private long id;
    private Name name;
    private LocalDate date;
    private ReservationTime time;

    public Reservation() {
    }

    public Reservation(long id, Name name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(Name name, LocalDate date, ReservationTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(long id, Reservation reservation) {
        this(id, reservation.name, reservation.date, reservation.time);
    }

    public static Reservation toEntity(long id, Reservation reservation) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    public long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
