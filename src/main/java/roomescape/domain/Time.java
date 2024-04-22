package roomescape.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Time {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private final Long id;
    private final LocalTime startAt;

    public Time(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Time(final Long id, final String startAt) {
        this(id, LocalTime.parse(startAt, TIME_FORMAT));
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
