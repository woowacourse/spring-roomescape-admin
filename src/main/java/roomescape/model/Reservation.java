package roomescape.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDateTime reservationTime;

    private Reservation(Long id, String name, LocalDateTime reservationTime) {
        this.id = id;
        this.name = name;
        this.reservationTime = reservationTime;
    }

    public static Reservation of(Long id, String name, LocalDate date, LocalTime time) {
        return new Reservation(id, name, LocalDateTime.of(date, time));
    }

    public static Reservation withId(Long id, Reservation reservation) {
        return new Reservation(id, reservation.getName(), reservation.reservationTime);
    }

    public static Reservation withoutId(String name, LocalDateTime reservationTime) {
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
}
