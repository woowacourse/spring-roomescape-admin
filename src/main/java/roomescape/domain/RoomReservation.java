package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class RoomReservation {

    private final String name;
    private final LocalDate date;
    private final LocalTime time;
    private Long id;

    public RoomReservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public RoomReservation(String name, LocalDate date, LocalTime time) {
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

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
