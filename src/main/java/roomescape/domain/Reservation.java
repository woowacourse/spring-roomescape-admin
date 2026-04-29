package roomescape.domain;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private Time reservationTime;

    public Reservation() {
    }

    public Reservation(Long id, String name, String date, Time reservationTime) {
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

    public Time getReservationTime() {
        return reservationTime;
    }
}
