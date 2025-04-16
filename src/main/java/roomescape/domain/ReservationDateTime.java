package roomescape.domain;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationDateTime {
    private final LocalDate date;
    private final LocalTime time;

    public ReservationDateTime(final LocalDate date, final LocalTime time, final Clock clock) {
        validateDateTime(date, time, clock);
        this.date = date;
        this.time = time;
    }

    private void validateDateTime(final LocalDate date, final LocalTime time, final Clock clock) {
        LocalDateTime reservationDatetime = LocalDateTime.of(date, time);
        if (!reservationDatetime.isAfter(LocalDateTime.now(clock))) {
            throw new IllegalArgumentException("예약은 현재 일시 이후여야 합니다.");
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
