package roomescape.entity;

public class Reservation {
    private final long id;
    private final String name;
    private final String date;
    private final ReservationTime reservationTime;

    public Reservation(long id, String name, String date, ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
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

    public ReservationTime getReservationTime() {
        return reservationTime;
    }
}
