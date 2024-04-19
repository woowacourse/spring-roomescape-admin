package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final Long id;
    private final Name name;
    private final ReserveTime reserveTime;

    private Reservation(Long id, Name name, ReserveTime reserveTime) {
        this.id = id;
        this.name = name;
        this.reserveTime = reserveTime;
    }

    private Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this(id, new Name(name), new ReserveTime(date, time));
    }

    private Reservation(Long id, String name, String date, String time) {
        this(id, name, LocalDate.parse(date), LocalTime.parse(time));
    }

    public Reservation(String name, String date, String time) {
        this(null, name, date, time);
    }

    public Reservation(Long id, Reservation reservation) {
        this(id, reservation.name, reservation.reserveTime);
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public ReserveTime getReserveTime() {
        return reserveTime;
    }
}
