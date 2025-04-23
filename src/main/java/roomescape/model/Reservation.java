package roomescape.model;

import java.time.LocalDate;

public record Reservation(Long id, String name, LocalDate date, ReservationTime reservationTime) {

    public Reservation {
        validateName(name);
        validateDate(date);
        validateReservationTime(reservationTime);
    }

    public boolean isSameId(Long id) {
        return this.id == id;
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank() || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid name");
        }
    }

    private static void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Invalid date");
        }
    }

    private static void validateReservationTime(ReservationTime reservationTime) {
        if (reservationTime == null) {
            throw new IllegalArgumentException("Invalid reservation");
        }
    }
}
