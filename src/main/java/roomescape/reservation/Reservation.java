package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public record Reservation(
        long id, String name, LocalDate date, LocalTime time
) {

    public Reservation {
        Objects.requireNonNull(name);
        Objects.requireNonNull(date);
        Objects.requireNonNull(time);
    }

    public Reservation writeId(final long id) {
        return new Reservation(id, name, date, time);
    }
}
