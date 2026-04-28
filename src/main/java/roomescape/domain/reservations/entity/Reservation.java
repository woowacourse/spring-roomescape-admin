package roomescape.domain.reservations.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(String name, String date, String time) {
        this.name = name;
        this.date = parseLocalDate(date);
        this.time = parseTime(time);
    }

    public LocalDate parseLocalDate(String date) {
        return LocalDate.parse(date);
    }

    public LocalTime parseTime(String time) {
        return LocalTime.parse(time);
    }
}
