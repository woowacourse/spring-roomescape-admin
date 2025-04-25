package roomescape.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import roomescape.exception.DomainException;

public final class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        validateNotBlankName(name);
        validateNotNullDateTime(date, time);
        validateNotPastDateTime(LocalDateTime.of(date, time.getStartAt()));
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, LocalDate date, ReservationTime time) {
        this(null, name, date, time);
    }

    private void validateNotNullDateTime(LocalDate date, ReservationTime time) {
        if (date == null) {
            throw new DomainException("예약 날짜가 입력되지 않았습니다.");
        }
        if (time == null) {
            throw new DomainException("예약 시간이 입력되지 않았습니다.");
        }
    }

    private void validateNotBlankName(String name) {
        if (name == null || name.isBlank()) {
            throw new DomainException("예약자명이 입력되지 않았습니다.");
        }
    }

    private void validateNotPastDateTime(LocalDateTime reservationDateTime) {
        if (reservationDateTime.isBefore(LocalDateTime.now())) {
            throw new DomainException("과거 일시로 예약을 생성할 수 없습니다.");
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
