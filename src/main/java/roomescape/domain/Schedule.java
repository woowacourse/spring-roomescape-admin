package roomescape.domain;

import roomescape.exception.InvalidReservationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Schedule {
    private static final String INVALID_SCHEDULE = "현재보다 이전으로 일정을 설정할 수 없습니다.";

    private final LocalDate date;
    private final ReservationTime time;

    public Schedule(final LocalDate date, final ReservationTime time) {
        validate(date, time);
        this.date = date;
        this.time = time;
    }

    public Schedule(final String date, final ReservationTime reservationTime) {
        this(LocalDate.parse(date), reservationTime);
    }

    private void validate(final LocalDate date, ReservationTime time) {
        LocalDateTime value = LocalDateTime.of(date, LocalTime.parse(time.getStartAt()));
        if (value.isBefore(LocalDateTime.now())) {
            throw new InvalidReservationException(INVALID_SCHEDULE);
        }
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ISO_DATE);
    }

    public String getTime() {
        return time.getStartAt();
    }

    public ReservationTime getReservationTime() {
        return time;
    }
}
