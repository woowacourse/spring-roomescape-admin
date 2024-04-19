package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Schedule {
    private static final String TIME_FORMAT = "HH:mm";
    private static final String INVALID_SCHEDULE = "현재보다 이전으로 일정을 설정할 수 없습니다.";

    private final LocalDateTime value;

    public Schedule(final String date, String time) {
        LocalDateTime value = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time));
        validate(value);
        this.value = value;
    }

    private void validate(final LocalDateTime value) {
        if (value.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(INVALID_SCHEDULE);
        }
    }

    public String getDate() {
        return value.format(DateTimeFormatter.ISO_DATE);
    }

    public String getTime() {
        return value.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }
}
