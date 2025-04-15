package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public record Reservation(Long id, String name, LocalDate date, LocalTime time) {

    public Reservation {
        validateNotNull(id, name, date, time);
    }

    private void validateNotNull(final Long id, final String name, final LocalDate date, final LocalTime time) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(date);
        Objects.requireNonNull(time);
    }
}
