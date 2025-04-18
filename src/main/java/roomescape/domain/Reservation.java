package roomescape.domain;

import java.time.LocalDateTime;

public class Reservation {

    private Long id;
    private final String name;
    private final LocalDateTime dateTime;


    public Reservation(String name, LocalDateTime dateTime) {
        this.name = name;
        this.dateTime = dateTime;
    }

    public Reservation(final Long id, final String name, final LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public static Reservation createReservationWithId(final Long id, Reservation reservation) {
        return new Reservation(id, reservation.getName(), reservation.getDateTime());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
