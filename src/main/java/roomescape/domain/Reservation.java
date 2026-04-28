package roomescape.domain;

import java.time.LocalDate;

public class Reservation {

    private final long id;
    private final String name;
    private final LocalDate reservationDate;
    private final ReservationTime reservationTime;

    private Reservation(long id, String name, LocalDate date, ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.reservationDate = date;
        this.reservationTime = reservationTime;
    }

    public static Reservation create(long id, String username, LocalDate date, ReservationTime time) {
        return new Reservation(id, username, date, time);
    }

    public String username() {
        return name;
    }

    public LocalDate date() {
        return reservationDate;
    }

    public ReservationTime time() {
        return reservationTime;
    }

    public long id() {
        return id;
    }
}
