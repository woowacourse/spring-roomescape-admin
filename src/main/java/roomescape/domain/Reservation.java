package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation() {
    }

    public Reservation(final String name, final LocalDate date, final LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final Long id, final String name, final LocalDate date, final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date.toString();
    }

    public String getTime() {
        return time.toString();
    }
}
