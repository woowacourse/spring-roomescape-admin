package roomescape.model;

import java.time.LocalDateTime;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDateTime reservationTime;

    public Reservation(Long id, String name, LocalDateTime reservationTime) {
        this.id = id;
        this.name = name;
        this.reservationTime = reservationTime;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public boolean sameId(Long id){
        return this.id.equals(id);
    }
}
