package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final Long id;
    private final Name name;
    private final ReserveTime reserveTime;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = new Name(name);
        this.reserveTime = new ReserveTime(date, time);
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
