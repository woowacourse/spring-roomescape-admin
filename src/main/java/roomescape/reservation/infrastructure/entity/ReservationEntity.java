package roomescape.reservation.infrastructure.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationEntity {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    private ReservationEntity(final Long id, final String name, final LocalDate date, final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationEntity of(final Long id, final String name, final LocalDate date, final LocalTime time) {
        return new ReservationEntity(id, name, date, time);
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

    public LocalTime getTime() {
        return time;
    }
}
