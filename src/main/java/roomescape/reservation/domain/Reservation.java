package roomescape.reservation.domain;

import roomescape.time.domain.ReservationTime;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private ReservationTime time;

    public Reservation() {
    }

    public Reservation(final Long id, final Reservation reservation) {
        this(id, reservation.name, reservation.date, reservation.time);
    }

    public Reservation(final Long id, final String name, final String date, final ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final String name, final String date, final ReservationTime time) {
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

    public String getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
