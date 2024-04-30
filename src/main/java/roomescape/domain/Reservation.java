package roomescape.domain;

public class Reservation {

    private final Long id;
    private final String name;
    private final String date;
    private final ReservationTime reservationTime;

    public Reservation(Long id, String name, String date, ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
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

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", reservationTime=" + reservationTime +
                '}';
    }
}
