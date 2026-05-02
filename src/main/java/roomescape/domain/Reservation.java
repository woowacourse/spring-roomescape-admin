package roomescape.domain;

import java.time.LocalDate;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    public Reservation() {
    }

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public Reservation(String name, LocalDate date, ReservationTime time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public static Reservation of(long id, String name, LocalDate date, ReservationTime time) {
        return new Reservation(id, name, date, time);
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ReservationTime getTime() {
        return time;
    }
}
