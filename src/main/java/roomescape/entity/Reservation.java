package roomescape.entity;

public class Reservation {

    private final long id;
    private final String name;
    private final String date;
    private final ReservationTime time;

    public Reservation(long id, String name, String date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(long id, Reservation reservation) {
        this(id, reservation.getName(), reservation.getDate(), reservation.getTime());
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
