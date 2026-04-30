package roomescape.reservation.domain;

import java.time.LocalDate;
import roomescape.time.domain.ReservationTime;

public class Reservation {

    private final String name;
    private final LocalDate date;
    private final ReservationTime time;
    private Long id;

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, LocalDate date, ReservationTime time) {
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

    public Long getTimeId() {
        return time.getId();
    }

    public ReservationTime getTime() {
        return time;
    }
}
