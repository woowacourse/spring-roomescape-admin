package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.dto.ReservationRequest;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation() {
    }

    public Reservation(Long id, String name, LocalDate date, LocalTime reservationTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = reservationTime;
    }

    public Reservation(String name, LocalDate date, LocalTime reservationTime) {
        this.name = name;
        this.date = date;
        this.time = reservationTime;
    }

    public Long getId() {
        return id;
    }

    public String getName(){
        return this.name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
