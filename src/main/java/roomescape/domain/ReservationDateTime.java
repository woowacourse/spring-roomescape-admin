package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class ReservationDateTime {
    private final LocalDate date;
    private final LocalTime time;

    public ReservationDateTime(final LocalDate date, final LocalTime time) {
        this.date = Objects.requireNonNull(date, "예약 날짜는 null일 수 없습니다.");
        this.time = Objects.requireNonNull(time, "예약 시간은 null일 수 없습니다.");
    }

    public LocalDate date() {
        return date;
    }

    public LocalTime time() {
        return time;
    }

    public boolean isAfter(final LocalDateTime now) {
        return LocalDateTime.of(date, time).isAfter(now);
    }
}
