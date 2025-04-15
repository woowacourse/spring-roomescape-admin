package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class Reservation {

    private final Long id;
    private final String userName;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(Long id, String userName, LocalDate date, LocalTime time) {
        this.id = id;
        this.userName = userName;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
