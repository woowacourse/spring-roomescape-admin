package roomescape.model;

import java.time.LocalDate;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(final Long id, final String name, final LocalDate date, final ReservationTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(final String name, final LocalDate date, final ReservationTime reservationTime) {
        validateNull(name, date, reservationTime);
        validateBlank(name);
    }

    private void validateNull(final String name, final LocalDate date, final ReservationTime reservationTime) {
        if (name == null || date == null || reservationTime == null) {
            throw new IllegalArgumentException("예약 생성에 필요한 값이 존재하지 않습니다.");
        }
    }

    private void validateBlank(final String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("예약자명이 존재하지 않습니다.");
        }
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

    public ReservationTime getTime() {
        return time;
    }
}
