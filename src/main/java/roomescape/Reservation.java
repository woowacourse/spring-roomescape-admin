package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation() {
    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public Reservation(String name, LocalDate date, LocalTime time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
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

    public LocalTime getTime() {
        return time;
    }
}
