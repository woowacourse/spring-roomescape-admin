package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {

    private final Long id;
    private final String name;
    private final ReservationDateTime reservationDateTime;

    public Reservation(final Long id, final String name, final ReservationDateTime reservationDateTime) {
        this.id = id;
        this.name = name;
        this.reservationDateTime = reservationDateTime;
    }

    public Reservation(final Long id, final Reservation reservation) {
        this(id, reservation.name, reservation.reservationDateTime);
    }

    public boolean isEqualId(final Long id) {
        return this.id.equals(id);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return reservationDateTime.getDate();
    }

    public LocalTime getTime() {
        return reservationDateTime.getTime();
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
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(reservationDateTime, that.reservationDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, reservationDateTime);
    }
}
