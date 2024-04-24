package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public class ReservationResponse {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public ReservationResponse(Reservation reservation) {
        this(reservation.getId(), reservation.getName().getName(), reservation.getDate(), reservation.getTime());
    }

    public ReservationResponse(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
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
