package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    private Reservation() {
    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(LocalDate date, String name, LocalTime time) {
        this(null, name, date, time);
    }

    public static Reservation toEntity(long id, Reservation reservation) {
        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
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
