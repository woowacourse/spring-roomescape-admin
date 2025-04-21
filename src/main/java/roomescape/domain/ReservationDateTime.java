package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationDateTime {
    private final LocalDate date;
    private final ReservationTime time;

    private ReservationDateTime(final LocalDate date, final ReservationTime time) {
        this.date = date;
        this.time = time;
    }

    public static ReservationDateTime of(final LocalDate date, final ReservationTime time) {
        return new ReservationDateTime(date, time);
    }

    public static ReservationDateTime createNewReservationTime(final LocalDate date, final ReservationTime time,
                                                               final LocalDateTime now) {
        validateFutureTime(date, time, now);
        return new ReservationDateTime(date, time);
    }

    private static void validateFutureTime(final LocalDate date, final ReservationTime time, final LocalDateTime now) {
        if (date.isBefore(now.toLocalDate())) {
            throw new IllegalArgumentException("예약은 현재 일시 이후여야 합니다.");
        }
        if (date.isEqual(now.toLocalDate()) && !time.isAfter(now.toLocalTime())) {
            throw new IllegalArgumentException("예약은 현재 일시 이후여야 합니다.");
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
