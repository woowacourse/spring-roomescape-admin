package roomescape.reservation.entity;

import java.time.LocalDate;
import roomescape.time.entity.ReservationTime;

public class Reservation {

    private Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    private Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation createNew(String name, LocalDate date, ReservationTime time) {
        return new Reservation(null, name, date, time);
    }

    public static Reservation of(Long id, String name, LocalDate date, ReservationTime time) {
        return new Reservation(id, name, date, time);
    }

    public Reservation withId(Long id) {
        return new Reservation(id, this.name, this.date, this.time);
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
