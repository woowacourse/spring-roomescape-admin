package roomescape.domain;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(long id, String name, String date, String time) {
        this(id, name, LocalDate.parse(date), LocalTime.parse(time));
    }

    public Reservation(long id, String name, Date date, Time time) {
        this(id, name, date.toLocalDate(), time.toLocalTime());
    }

    public Reservation(long id, String name, LocalDate date, LocalTime time) {
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
}
