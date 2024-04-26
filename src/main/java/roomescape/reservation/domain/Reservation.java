package roomescape.reservation.domain;

import java.time.LocalDate;
import roomescape.time.domain.ReservationTime;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime reservationTime;

    public Reservation(Long id, String name, LocalDate date, ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    public boolean isSameId(Long id) {
        return this.id.equals(id);
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
        return reservationTime;
    }
}
