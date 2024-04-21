package roomescape.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Time {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final Long id;
    private final LocalTime startAt;

    public Time(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public String toStringStartAt() {
        return startAt.format(TIME_FORMATTER);
    }

    public Long getId() {
        return id;
    }
}
