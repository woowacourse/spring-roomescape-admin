package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {

    private final long id;
    private final String reservationUsername;
    private final LocalDateTime dateTime;

    private Reservation(long id, String reservationUsername, LocalDateTime dateTime) {
        this.id = id;
        this.reservationUsername = reservationUsername;
        this.dateTime = dateTime;
    }

    public static Reservation createReservation(long id, String username, LocalDateTime dateTime) {
        return new Reservation(id, username, dateTime);
    }

    public String username() {
        return reservationUsername;
    }

    public LocalDate date() {
        return dateTime.toLocalDate();
    }

    public LocalTime time() {
        return dateTime.toLocalTime();
    }

    public long id() {
        return id;
    }
}
