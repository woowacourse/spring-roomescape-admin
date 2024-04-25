package roomescape.dto.reservation;

import java.time.LocalDate;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.Time;

public class ReservationResponse {

    private final Long id;

    private final String name;

    private final LocalDate date;

    private final Time time;

    private ReservationResponse(Long id, String name, LocalDate date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(),
                reservation.getDate(), reservation.getTime());
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

    public Time getTime() {
        return time;
    }
}
