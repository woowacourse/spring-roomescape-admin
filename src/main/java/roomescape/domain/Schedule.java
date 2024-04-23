package roomescape.domain;

import roomescape.exception.InvalidReservationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Schedule {
    private static final String INVALID_SCHEDULE = "현재보다 이전으로 일정을 설정할 수 없습니다.";

    private final LocalDate date;
    private final ReservationTime time;

    public Schedule(final String date, final ReservationTime time) {
        validate(date, time);
        this.date = LocalDate.parse(date);
        this.time = time;
    }

    private void validate(final String date, ReservationTime time) {
        validateIfInvalidDate(date);
        validateIfBeforeCurrent(date, time);
    }

    private void validateIfInvalidDate(final String date) {
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidReservationException("올바르지 않은 날짜입니다. date: '" + date + "'");
        }
    }

    private static void validateIfBeforeCurrent(final String date, final ReservationTime time) {
        LocalDateTime value = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time.getStartAt()));
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
