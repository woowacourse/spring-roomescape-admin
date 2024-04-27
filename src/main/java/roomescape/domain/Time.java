package roomescape.domain;

import java.time.LocalTime;

public class Time {
    private final long id;
    private final LocalTime startTime;

    public Time(long id, LocalTime startTime) {
        this.id = id;
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
}
