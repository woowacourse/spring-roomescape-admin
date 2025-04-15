package roomescape.domain;

import java.util.Objects;

public class Reservation {
    private final Long id;
    private final Person person;
    private final ReservationTime reservationTime;

    public Reservation(long id, Person person, ReservationTime reservationTime) {
        this.id = id;
        this.person = person;
        this.reservationTime = reservationTime;
    }

    public long getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return id.equals(that.id) && Objects.equals(person, that.person)
            && Objects.equals(reservationTime, that.reservationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, reservationTime);
    }
}
