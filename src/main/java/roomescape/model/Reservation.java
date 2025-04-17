package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(Long id, String name, LocalDate date, LocalTime time) {

    public Reservation {
        validateNotNull(id, name, date, time);
        validateName(name);
    }

    private void validateNotNull(Long id, String name, LocalDate date, LocalTime time) {
        if (id == null || name == null || date == null || time == null) {
            throw new IllegalArgumentException("Invalid reservation");
        }
    }

    private void validateName(String name) {
        if (name.isBlank() || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid name");
        }
    }
}
