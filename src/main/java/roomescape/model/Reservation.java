package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(String name, LocalDate date, LocalTime time) {
        validate(name, date, time);
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private Reservation(Long id, String name, LocalDate date, LocalTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    private void validate(String name, LocalDate date, LocalTime time) {
        validateNotNull(name, date, time);
        validateName(name);
    }

    private void validateNotNull(String name, LocalDate date, LocalTime time) {
        if (name == null || date == null || time == null) {
            throw new IllegalArgumentException("Invalid reservation");
        }
    }

    private void validateName(String name) {
        if (name.isBlank() || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid name");
        }
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
}
