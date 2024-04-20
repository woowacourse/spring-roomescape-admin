package roomescape.domain;

import java.util.Objects;

public class Reservation {

    private final Long id;
    private final Name name;
    private final ReservationDate reservationDate;
    private final ReservationTime reservationTime;

    private Reservation(Long id, Name name, ReservationDate reservationDate, ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }
    public Reservation(Long id, String name, String date, ReservationTime reservationTime) {
        this(id, new Name(name), new ReservationDate(date), reservationTime);
    }

    public Reservation(String name, String date, ReservationTime reservationTime) {
        this(null, name, date, reservationTime);
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public ReservationDate getReservationDate() {
        return reservationDate;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
