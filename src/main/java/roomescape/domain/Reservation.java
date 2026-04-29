package roomescape.domain;

public class Reservation {
    Long id;
    Name name;
    ReservationDate date;
    ReservationTime time;

    public Reservation(Long id, Name name, ReservationDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(Name name, ReservationDate date, ReservationTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
