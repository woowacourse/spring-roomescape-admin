package roomescape.domain.reservation.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;
    private final Long timeId;

    public Reservation(String name, LocalDate date, LocalTime time) {
        this.id = null;
        this.name = name;
        this.date = date;
        this.time = time;
        this.timeId = null;
    }

    public Reservation(String name, LocalDate date, Long timeId) {
        this.id = null;
        this.name = name;
        this.date = date;
        this.time = null;
        this.timeId = timeId;
    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.timeId = null;
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

    public Long getTimeId() {
        return timeId;
    }
}
