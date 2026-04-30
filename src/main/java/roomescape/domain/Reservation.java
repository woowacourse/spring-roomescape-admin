package roomescape.domain;

public class Reservation {
    private final Long id;
    private final Name name;
    private final ReservationDate date;
    private final ReservationTime time;

    public Reservation(Long id, Name name, ReservationDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.value();
    }

    public String getDate() {
        return date.value();
    }

    public ReservationTime getTime() {
        return time;
    }
}
