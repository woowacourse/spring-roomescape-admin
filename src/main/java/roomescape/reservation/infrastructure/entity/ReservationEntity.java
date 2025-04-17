package roomescape.reservation.infrastructure.entity;

import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationEntity {
    private Long id;
    private final String name;
    private final LocalDateTime dateTime;

    public ReservationEntity(final Long id, final String name, final LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public static ReservationEntity from(Reservation reservation) {
        return createWithoutId(reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public static ReservationEntity createWithoutId(String name, LocalDate date, LocalTime time) {
        return new ReservationEntity(null, name, LocalDateTime.of(date, time));
    }

    public Reservation toDomain() {
        return Reservation.createWithId(id, name, dateTime);
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }
}
