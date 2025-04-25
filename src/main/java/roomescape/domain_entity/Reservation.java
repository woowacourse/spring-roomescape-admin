package roomescape.domain_entity;

import java.time.LocalDate;

public class Reservation {
    private Id id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    public Reservation() {
    }

    public Reservation(String name, LocalDate date, ReservationTime time) {
        this.id = Id.empty();
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(Id id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public void setTime(ReservationTime reservationTime) {
        this.time = reservationTime;
    }

    public long getId() {
        return id.value();
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
