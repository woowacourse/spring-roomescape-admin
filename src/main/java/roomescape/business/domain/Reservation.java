package roomescape.business.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    private Long id;

    private final String name;
    private final LocalDate date;
    private final Time time;

    public Reservation(final String name, final LocalDate date, final Time time) {
        validateNonNull(name, date, time);
        validateNameIsNotBlank(name);
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final Long id, final String name, final LocalDate date, final Time time) {
        validateNonNull(id, name, date, time);
        validateNameIsNotBlank(name);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateNameIsNotBlank(final String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("name이 empty 입니다.");
        }
    }

    private void validateNonNull(
            final String name, final LocalDate date, final Time time
    ) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(date);
        Objects.requireNonNull(time);
    }

    private void validateNonNull(
            final Long id, final String name, final LocalDate date, final Time time)
    {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(date);
        Objects.requireNonNull(time);
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

    public Time getTime() {
        return time;
    }
}
