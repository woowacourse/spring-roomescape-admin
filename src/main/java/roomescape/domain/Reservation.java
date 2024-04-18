package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final Name name;
    private final ReserveTime reserveTime;

    public Reservation(String name, LocalDate date, LocalTime time) {
        this.name = new Name(name);
        this.reserveTime = new ReserveTime(date, time);
    }

    public Reservation(String name, String date, String time) {
        this(name, LocalDate.parse(date), LocalTime.parse(time));
    }

    public Name getName() {
        return name;
    }

    public ReserveTime getReserveTime() {
        return reserveTime;
    }
}
