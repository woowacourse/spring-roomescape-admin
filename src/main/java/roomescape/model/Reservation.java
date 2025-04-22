package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(Long id, String name, LocalDate date, LocalTime time) {

    public Reservation {
        validateId(id);
        validateName(name);
        validateDateAndTime(date, time);
    }

    public static Reservation withoutId(String name, LocalDate date, LocalTime time) {
        validateName(name);
        validateDateAndTime(date, time);
        return new Reservation(null, name, date, time);
    }

    public boolean isSameId(Long id) {
        return this.id == id;
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
    }

    private static void validateDateAndTime(LocalDate date, LocalTime time) {
        if (date == null || time == null) {
            throw new IllegalArgumentException("Invalid reservation");
        }
    }

    private static void validateName(String name) {
        if (name.isBlank() || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid name");
        }
    }
}
