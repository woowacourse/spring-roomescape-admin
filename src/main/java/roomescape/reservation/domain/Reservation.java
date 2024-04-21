package roomescape.reservation.domain;

import java.time.LocalDate;
import roomescape.reservation.dto.request.ReservationRequest;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation from(ReservationRequest reservationRequest, ReservationTime reservationTime) {
        return new Reservation(null, reservationRequest.name(), reservationRequest.date(), reservationTime);
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

    public ReservationTime getTime() {
        return time;
    }

    public Long getTimeId() {
        return time.getId();
    }
}
