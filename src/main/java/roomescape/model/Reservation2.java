package roomescape.model;

import java.time.LocalDate;

public class Reservation2 {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    private Reservation2(final Long id, final String name, final LocalDate date, final ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation2 create(final String name, final String date, final ReservationTime time) {
        return new Reservation2(null, name, LocalDate.parse(date), time);
    }

    public Reservation2 toReservation(final long id) {
        return new Reservation2(id, name, date, time);
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
