package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {

    private final ReservationId id;
    private final String name;
    private final ReservationDateTime datetime;

    public Reservation(final ReservationId id, final String name, final ReservationDateTime datetime) {
        this.id = id;
        this.name = name;
        this.datetime = datetime;
    }

    public static Reservation createWithId(Long id, String name, LocalDateTime dateTime) {
        return new Reservation(ReservationId.from(id), name, ReservationDateTime.from(dateTime));
    }

    public static Reservation createWithoutId(String name, LocalDateTime dateTime) {
        return createWithId(null, name, dateTime);
    }

    public Long getId() {
        return id.getValue();
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return datetime.getDate();
    }

    public LocalTime getTime() {
        return datetime.getTime();
    }
}
