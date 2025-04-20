package roomescape.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.exception.ReservationException;

public final class Reservation {

    private static final int MAX_NAME_LENGTH = 20;

    private final Integer id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(Integer id, String name, LocalDate date, LocalTime time) {
        validateNameLength(name);
        validateNotPastDateTime(LocalDateTime.of(date, time));
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, LocalDate date, LocalTime time) {
        validateNameLength(name);
        validateNotPastDateTime(LocalDateTime.of(date, time));
        this.id = null;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateNameLength(String name) {
        if (name.isEmpty() || MAX_NAME_LENGTH < name.length()) {
            throw new ReservationException("예약자명은 1자 이상 %d자 이하로만 가능합니다.".formatted(MAX_NAME_LENGTH));
        }
    }

    private void validateNotPastDateTime(LocalDateTime reservationDateTime) {
        if (reservationDateTime.isBefore(LocalDateTime.now())) {
            throw new ReservationException("과거 일시로 예약을 생성할 수 없습니다.");
        }
    }

    public Reservation createWithId(Integer id) {
        return new Reservation(id, name, date, time);
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
