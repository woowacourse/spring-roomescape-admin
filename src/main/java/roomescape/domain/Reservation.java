package roomescape.domain;

public class Reservation {

    private final long id;
    private final String name;
    private final String date;
    private final ReservationTime time;

    private Reservation(final long id, final String name, final String date, final ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(final long id, final String name, final String date, final ReservationTime time) {
        return new Reservation(id, name, date, time);
    }

    public long getId() {
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
