package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public record Reservation(Long id, String name, LocalDate date, LocalTime time) {

    public Reservation {
        validateNotNull(id, name, date, time);
        validateName(name);
    }

    private void validateNotNull(Long id, String name, LocalDate date, LocalTime time) {
        if (Objects.isNull(id) || Objects.isNull(name) || Objects.isNull(date) || Objects.isNull(time)) {
            throw new IllegalArgumentException("Invalid reservation");
        }
    }

    private void validateName(String name) {
        if (name.isBlank() || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid name");
        }
    }
}
