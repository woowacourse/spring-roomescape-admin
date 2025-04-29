package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private static final String VALIDATION_MESSAGE = "올바른 요청이 아닙니다";

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(String name, LocalDate date, ReservationTime time) {
        try {
            validateName(name);
            validateDate(date);
            validateReservationDateTime(date, time);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
    }

    private void validateReservationDateTime(LocalDate date, ReservationTime time) {
        if (time == null) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        if (date.isBefore(nowDate)) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
        if (nowDate.equals(date) && time.isBefore(nowTime)) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        if (that.id == null || id == null) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return System.identityHashCode(this);
        }
        return Objects.hashCode(id);
    }
}
