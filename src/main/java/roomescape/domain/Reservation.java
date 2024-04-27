package roomescape.domain;

import roomescape.exception.NonEmptyDateException;
import roomescape.exception.NonEmptyNameException;

import java.time.LocalDate;

public class Reservation {

    private long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    public Reservation() {
    }

    public Reservation(long id, String name, LocalDate date) {
        this(id, name, date, new ReservationTime());
    }

    private Reservation(String name, LocalDate date, ReservationTime time) {
        this(-1, name, date, time);
    }

    private Reservation(long id, String name, LocalDate date, ReservationTime time) {
        validateNotNull(name, date);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateNotNull(String name, LocalDate date) {
        if (name == null) {
            throw new NonEmptyNameException();
        }
        if (name.isEmpty()) {
            throw new NonEmptyNameException();
        }
        if (date == null) {
            throw new NonEmptyDateException();
        }
    }

    public static Reservation from(String name, LocalDate date, long timeId) {
        ReservationTime reservationTime = new ReservationTime(timeId);
        return new Reservation(name, date, reservationTime);
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

    public void setTime(ReservationTime time) {
        this.time = time;
    }
}
