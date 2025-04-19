package roomescape.user.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(final Long id, final String name, final LocalDate date, final LocalTime time) {
        validateNotNull(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateNotNull(final String name, final LocalDate date, final LocalTime time) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (time == null) {
            throw new IllegalArgumentException("Time cannot be null");
        }
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Reservation that)) {
            return false;
        }

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
