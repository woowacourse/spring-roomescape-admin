package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;

public class ReservationResponse {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final TimeResponse time;

    public ReservationResponse(Reservation reservation) {
        this(reservation.getId(), reservation.getName().getName(), reservation.getDate(),
                new TimeResponse(reservation.getTime()));
    }

    public ReservationResponse(Long id, String name, LocalDate date, TimeResponse time) {
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

    public TimeResponse getTime() {
        return time;
    }
}
