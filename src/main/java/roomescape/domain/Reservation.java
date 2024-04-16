package roomescape.domain;

import roomescape.dto.ReservationResponse;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationResponse toDto() {
        return new ReservationResponse(id, name, date, time);
    }

    public boolean isSameId(Long id) {
        return this.id.equals(id);
    }
}
