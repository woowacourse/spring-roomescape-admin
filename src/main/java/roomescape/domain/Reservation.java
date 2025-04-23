package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class Reservation {

    private long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation() {

    }

    public Reservation(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(long id, String name, LocalDate date, LocalTime time) {
        this(name, date, time);
        this.id = id;
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
}
