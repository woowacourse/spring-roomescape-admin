package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {

    private final Long id;
    private final String name;
    private final ReservationDateTime reservationDateTime;

    public Reservation(final Long id, final String name, final ReservationDateTime reservationDateTime) {
        this.id = Objects.requireNonNull(id);
        this.name = validateNonBlank(name);
        this.reservationDateTime = reservationDateTime;
    }

    private String validateNonBlank(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException();
        }

        return name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return reservationDateTime.getDate();
    }

    public LocalTime getTime() {
        return reservationDateTime.getTime();
    }
}
