package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {

    private final Long id;
    private final String name;
    private final ReservationDateTime reservationDateTime;

    public Reservation(final Long id, final String name, final ReservationDateTime reservationDateTime) {
        validate(id, name, reservationDateTime);
        this.id = id;
        this.name = name;
        this.reservationDateTime = reservationDateTime;
    }

    private void validate(final Long id, final String name, final ReservationDateTime reservationDateTime) {
        try {
            Objects.requireNonNull(id);
            Objects.requireNonNull(name);
            Objects.requireNonNull(reservationDateTime);
            validateBlank(name);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void validateBlank(final String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("예약자명이 존재하지 않습니다.");
        }
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
