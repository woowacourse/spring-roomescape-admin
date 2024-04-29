package roomescape.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
    private Long id;
    private Name name;
    private ReservationDate date;
    private ReservationTime time;

    private Reservation() {
    }

    public Reservation(Name name, ReservationDate date, ReservationTime time) {
        this(null, name, date, time);
    }

    public Reservation(Long id, Name name, ReservationDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public LocalDate getDate() {
        return date.getDate();
    }

    public ReservationTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
