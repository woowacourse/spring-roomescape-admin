package roomescape.domain;

public class Reservation {

    private long id;
    private String name;
    private String date;
    private ReservationTime time;

    public Reservation(final long id, final String name, final String date, final ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(final long id, final String name, final String date, final ReservationTime time) {
        return new Reservation(id, name, date, time);
    }
}
