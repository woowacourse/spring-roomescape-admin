package roomescape.domain;

import roomescape.dto.ReservationResponse;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationResponse toDto() {
        return new ReservationResponse(id, name, date, time);
    }

    public Long getId() {
        return id;
    }
}
