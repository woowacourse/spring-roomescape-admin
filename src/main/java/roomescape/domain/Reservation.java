package roomescape.domain;

import java.time.LocalDate;

public class Reservation {

    private int id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ReservationTime getTime() {
        return time;
    }

    public void setTime(ReservationTime time) {
        this.time = time;
    }
}
