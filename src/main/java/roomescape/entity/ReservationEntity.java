package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationEntity {
    private long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public ReservationEntity(final long id, final String name, final LocalDate date, final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationEntity(final String name, final LocalDate date, final LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationEntity createWithoutId(String name, LocalDate date, LocalTime time) {
        return new ReservationEntity(name, date, time);
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(final LocalTime time) {
        this.time = time;
    }
}
