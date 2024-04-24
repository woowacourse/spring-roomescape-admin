package roomescape.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
    private Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime reservationTime;

    public Reservation(
            final String name,
            final LocalDate date,
            final ReservationTime reservationTime
    ) {
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    public Reservation(
            final Long id,
            final String name,
            final LocalDate date,
            final ReservationTime reservationTime
    ) {
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

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
