package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public final class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId() {
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

    private void validate(String name, LocalDate date, LocalTime time) {
        validateName(name);
        validateDate(date);
        validateTime(time);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 이름입니다.");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 날짜입니다.");
        }
    }

    private void validateTime(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 시간입니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, time);
    }
}
