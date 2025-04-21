package roomescape.reservation.infrastructure.entity;

import roomescape.reservation.domain.Reservation;

import java.time.LocalDateTime;

public class ReservationEntity {
    private final Long id;
    private final String name;
    private final LocalDateTime dateTime;

    private ReservationEntity(final Long id, final String name, final LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public static ReservationEntity of(final Long id, final String name, final LocalDateTime dateTime) {
        return new ReservationEntity(id, name, dateTime);
    }

    public static ReservationEntity of(final Long id, final Reservation reservation) {
        return ReservationEntity.of(id, reservation.getName(), reservation.getDatetime());
    }

    public Reservation toDomain() {
        return Reservation.of(id, name, dateTime);
    }

    public long getId() {
        return id;
    }
}
