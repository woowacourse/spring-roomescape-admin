package roomescape;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class Reservation {

    private final int id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(int id, String name, LocalDate date, LocalTime time) {
        validateNotBlankName(name);
        validateNotNullDateTime(date, time);
        validateNotPastDateTime(LocalDateTime.of(date, time));
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateNotNullDateTime(LocalDate date, LocalTime time) {
        if (date == null) {
            throw new IllegalArgumentException("예약 날짜가 입력되지 않았습니다.");
        }
        if (time == null) {
            throw new IllegalArgumentException("예약 시간이 입력되지 않았습니다.");
        }
    }

    private void validateNotBlankName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("예약자명이 입력되지 않았습니다.");
        }
    }

    private void validateNotPastDateTime(LocalDateTime reservationDateTime) {
        if (reservationDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("과거 일시로 예약을 생성할 수 없습니다.");
        }
    }

    public int getId() {
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
