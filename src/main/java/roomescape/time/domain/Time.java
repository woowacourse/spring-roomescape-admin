package roomescape.time.domain;

import java.time.LocalTime;

public class Time {

    private final Long id;
    private final LocalTime startAt;

    public Time(Long id, LocalTime time) {
        this.id = id;
        this.startAt = time;
    }

    public Time(LocalTime time) {
        this(null, time);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
