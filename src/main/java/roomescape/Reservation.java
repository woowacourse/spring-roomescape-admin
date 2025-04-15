package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    public Reservation(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
