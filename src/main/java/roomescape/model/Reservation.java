package roomescape.model;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(final Long id, final String name, final LocalDate date, final ReservationTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(final String name, final LocalDate date, final ReservationTime reservationTime) {
        try {
            Objects.requireNonNull(name);
            Objects.requireNonNull(date);
            Objects.requireNonNull(reservationTime);
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
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
