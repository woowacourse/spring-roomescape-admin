package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private long id;
    private Person person;
    private ReservationTime reservationTime;

    public Reservation() {
    }

    public Reservation(final long id, final Person person, final ReservationTime reservationTime) {
        this.id = id;
        this.person = person;
        this.reservationTime = reservationTime;
    }

    public Reservation(final Person person, final ReservationTime reservationTime) {
        this.person = person;
        this.reservationTime = reservationTime;
    }

    public String getPersonName() {
        return person.name();
    }

    public LocalDate getDate() {
        return reservationTime.getDate();
    }

    public LocalTime getTime() {
        return reservationTime.getTime();
    }

    public long getId() {
        return id;
    }

    public boolean isSameId(long id) {
        return this.id == id;
    }
}
