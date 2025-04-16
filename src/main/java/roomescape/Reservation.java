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
        validateNotPastDateTime(LocalDateTime.of(date, time));
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
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
