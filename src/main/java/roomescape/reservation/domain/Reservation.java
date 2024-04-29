package roomescape.reservation.domain;

import java.time.LocalDate;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    public Reservation(final Long id, final Reservation reservation) {
        this(id, reservation.name, reservation.date, reservation.time);
    }

    public Reservation(final Long id, final String name, final LocalDate date, final ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final String name, final LocalDate date, final ReservationTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
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
