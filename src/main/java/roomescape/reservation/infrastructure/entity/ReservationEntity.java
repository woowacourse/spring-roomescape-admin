package roomescape.reservation.infrastructure.entity;

import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationEntity {
    private final Long id;
    private final String name;
    private final LocalDateTime dateTime;

    public ReservationEntity(final Long id, final String name, final LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public static ReservationEntity of(Long id, Reservation reservation) {
        return new ReservationEntity(id, reservation.getName(), reservation.getDatetime());
    }

    public Reservation toDomain() {
        return Reservation.of(id, name, dateTime);
    }

    public long getId() {
        return id;
    }
}
