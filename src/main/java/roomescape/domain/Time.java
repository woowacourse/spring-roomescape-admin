package roomescape.domain;

import java.time.LocalTime;

public class Time {
    private final Long id;
    private final LocalTime startTime;

    public Time(Long id, LocalTime startTime) {
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
