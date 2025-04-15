package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(final Long id, final String name, final LocalDate date, final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(Long id, Reservation reservation) {
        this(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public boolean isEqualId(Long id) {
        return this.id.equals(id);
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

    public LocalTime getTime() {
        return time;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation that)) {
            return false;
        }
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
