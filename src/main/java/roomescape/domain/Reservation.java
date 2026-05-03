package roomescape.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
    private final Long id;
    private final Name name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = new Name(name);
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Reservation that)) {
            return false;
        }

        return Objects.equals(id, that.id);
    }
}
