package roomescape.domain;

public class Reservation {
    private final long id;
    private final Name name;
    private final ReservationDate date;
    private final ReservationTime time;

    public Reservation(long id, String name, String date, ReservationTime time) {
        this.id = id;
        this.name = Name.from(name);
        this.date = ReservationDate.from(date);
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public String getDate() {
        return date.toString();
    }

    public ReservationTime getTime() {
        return time;
    }
}
