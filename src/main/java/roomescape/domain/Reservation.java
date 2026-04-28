package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final long id;
    private final String name;
    private final LocalDate reservationDate;
    private final LocalTime reservationTime;

    private Reservation(long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.reservationDate = date;
        this.reservationTime = time;
    }

    public static Reservation create(long id, String username, LocalDate date, LocalTime time) {
        return new Reservation(id, username, date, time);
    }

    public String username() {
        return name;
    }

    public LocalDate date() {
        return reservationDate;
    }

    public LocalTime time() {
        return reservationTime;
    }

    public long id() {
        return id;
    }
}
