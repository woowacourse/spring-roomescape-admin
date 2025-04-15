package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

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

    public Reservation(long id, Reservation reservation) {
        this.id = id;
        this.name = reservation.name;
        this.date = reservation.date;
        this.time = reservation.time;
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
