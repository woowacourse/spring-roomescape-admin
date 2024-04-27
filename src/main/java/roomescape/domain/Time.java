package roomescape.domain;

import java.time.LocalTime;

public class Time {
    private long id;
    private final LocalTime startAt;

    public Time(LocalTime startAt) {
        this(0, startAt);
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

    public void setId(long id) {
        this.id = id;
    }
}
