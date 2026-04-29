package roomescape.domain;

import java.time.LocalDateTime;

public class Reservation {
    private final Long id;
    private final String reservationName;
    private final LocalDateTime reservationDateTime;

    public Reservation(Long id, String reservationName, LocalDateTime reservationDateTime) {
        this.id = id;
        this.reservationName = reservationName;
        this.reservationDateTime = reservationDateTime;
    }

    public Reservation(String reservationName, LocalDateTime reservationDateTime) {
        this(null, reservationName, reservationDateTime);
    }

    public Long getId() {
        return id;
    }

    public String getReservationName() {
        return reservationName;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }
}
