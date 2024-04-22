package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private final Long id;
    private final ReservationName name;
    private final ReservationDate date;
    private final ReservationTime time;

    public Reservation(final Long id, final String name, final LocalDate date, final LocalTime time) {
        this.id = id;
        this.name = new ReservationName(name);
        this.date = new ReservationDate(date);
        this.time = new ReservationTime(time);
    }

    public boolean isSameId(final Long id) {
        return Objects.equals(this.id, id);
    }

    public Long getId() {
        return id;
    }

    public ReservationName getName() {
        return name;
    }

    public ReservationDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
