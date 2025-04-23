package roomescape.domain;

public class Reservation {

    private final Long id;
    private final ReservationName name;
    private final ReservationDate date;
    private final ReservationTime time;

    public Reservation(final Long id, final ReservationName name, final ReservationDate date,
                       final ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final ReservationName name, final ReservationDate date, final ReservationTime time) {
        this(null, name, date, time);
    }

    public Long getId() {
        return id;
    }

    public ReservationName getName() {
        return name;
    }

    public ReservationDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
