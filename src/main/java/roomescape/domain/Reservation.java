package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(String name, String date, String time) {
        this.name = name;
        this.date = LocalDate.parse(date);
        this.time = LocalTime.parse(time);
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
