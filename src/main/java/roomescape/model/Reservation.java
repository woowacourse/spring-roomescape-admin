package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(Long id, String name, LocalDate date, LocalTime time) {

    public Reservation {
        validateReservation(id, name, date, time);
        validateName(name);
    }

    public boolean isSameId(Long id) {
        return this.id == id;
    }

    private void validateReservation(Long id, String name, LocalDate date, LocalTime time) {
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
