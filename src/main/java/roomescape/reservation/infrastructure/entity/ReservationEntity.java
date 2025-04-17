package roomescape.reservation.infrastructure.entity;

import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationEntity {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    private ReservationEntity(final Long id, final String name, final LocalDate date, final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationEntity from(Reservation reservation) {
        return createWithoutId(reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public static ReservationEntity createWithoutId(String name, LocalDate date, LocalTime time) {
        return new ReservationEntity(null, name, date, time);
    }

    public Reservation toDomain() {
        return Reservation.createWithId(id, name, date, time);
    }

    public long getId() {
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

    public void setId(final long id) {
        this.id = id;
    }
}
