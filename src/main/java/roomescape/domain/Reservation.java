package roomescape.domain;

public class Reservation {
    private String name;
    private String date;
    private ReservationTime time;

    public Reservation(String name, String date, ReservationTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
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
