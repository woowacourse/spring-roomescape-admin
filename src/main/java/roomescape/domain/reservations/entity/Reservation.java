package roomescape.domain.reservations.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(Long id, String name, String date, String time) {
        this.id = id;
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
