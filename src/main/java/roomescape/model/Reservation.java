package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.exception.reservation.ReservationFieldRequiredException;

public class Reservation {
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(String name, LocalDate date, LocalTime time) {
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
            throw new ReservationFieldRequiredException("이름");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new ReservationFieldRequiredException("날짜");
        }
    }

    private void validateTime(LocalTime time) {
        if (time == null) {
            throw new ReservationFieldRequiredException("시간");
        }
    }
}
