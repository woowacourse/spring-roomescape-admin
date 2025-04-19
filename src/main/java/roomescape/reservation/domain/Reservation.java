package roomescape.reservation.domain;

import roomescape.common.domain.DomainEntityId;

import java.time.LocalDateTime;

public class Reservation {

    private final DomainEntityId id;
    private final String name;
    private final ReservationDateTime datetime;

    public Reservation(final DomainEntityId id, final String name, final ReservationDateTime datetime) {
        this.id = id;
        this.name = name;
        this.datetime = datetime;
    }

    public static Reservation of(final Long id, final String name, final LocalDateTime dateTime) {
        return new Reservation(DomainEntityId.from(id), name, ReservationDateTime.from(dateTime));
    }

    public static Reservation of(final String name, final LocalDateTime dateTime) {
        return of(null, name, dateTime);
    }

    public Long getId() {
        return id.getValue();
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDatetime() {
        return datetime.getDateTime();
    }
}
