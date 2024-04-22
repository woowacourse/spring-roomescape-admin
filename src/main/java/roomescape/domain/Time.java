package roomescape.domain;

import java.time.LocalTime;

public class Time {
    private final long id;
    private final LocalTime startAt;

    public Time(long id, String startAt) {
        this(id, LocalTime.parse(startAt));
    }

    public Time(long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
