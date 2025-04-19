package roomescape.entity;

import java.time.LocalDateTime;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDateTime reservationDateTime;

    public Reservation(final Long id, final String name, final LocalDateTime reservationDateTime) {
        this.id = id;
        this.name = name;
        this.reservationDateTime = reservationDateTime;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }
}
