package roomescape.vo;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationCreateVo {
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public ReservationCreateVo(String name, LocalDate date, LocalTime time) {
        validate(name, date, time);
        this.name = name;
        this.date = date;
        this.time = time;
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
            throw new IllegalArgumentException("[ERROR]");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("[ERROR]");
        }
    }

    private void validateTime(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("[ERROR]");
        }
    }
}
