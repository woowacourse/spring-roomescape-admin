package roomescape.domain;

import java.time.LocalDate;
import java.util.Objects;
import roomescape.domain.time.ReservationTime;

public class Reservation {

    private final String name;
    private final LocalDate date;
    private final ReservationTime time;
    private Long id;

    public Reservation(
        final Long id,
        final String name,
        final LocalDate date,
        final ReservationTime time
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(
        final String name,
        final LocalDate date,
        final ReservationTime time
    ) {
        this(null, name, date, time);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Reservation that)) {
            return false;
        }
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
            && Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, time);
    }

    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", date=" + date +
            ", time=" + time +
            '}';
    }
}
