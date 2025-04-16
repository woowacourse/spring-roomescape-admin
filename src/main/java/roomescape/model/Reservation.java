package roomescape.model;

import java.time.LocalDateTime;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDateTime reservationTime;

    private Reservation(Long id, String name, LocalDateTime reservationTime) {
        this.id = id;
        this.name = name;
        this.reservationTime = reservationTime;
    }

    public static Reservation createReservation(Long id, String name, LocalDateTime reservationTime) {
        return new Reservation(id, name, reservationTime);
    }

    public static Reservation createReservationWithId(Long id, Reservation reservation) {
        return new Reservation(id, reservation.getName(), reservation.reservationTime);
    }

    public static Reservation createReservationWithoutID(String name, LocalDateTime reservationTime) {
        return new Reservation(null, name, reservationTime);
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

    public boolean sameId(Long id) {
        return this.id.equals(id);
    }
}
