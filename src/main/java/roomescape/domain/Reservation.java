package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final Name name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(){
        this.id = null;
        this.name = null;
        this.date = null;
        this.time = null;
    }

    public Reservation(Long id, Name name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = new Name(name);
        this.date = date;
        this.time = time;
    }

    public Reservation(long id, String name, String date, String time) {
        this.id = id;
        this.name = new Name(name);
        this.date = LocalDate.parse(date);
        this.time = LocalTime.parse(time);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
