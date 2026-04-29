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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.value();
    }

    public String getDate() {
        return date.value();
    }

    public String getTime() {
        return time.value();
    }
}
