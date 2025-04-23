package roomescape.model;

import java.time.LocalTime;

public class Time {

    private final Long id;
    private final LocalTime startAt;

    private Time(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static Time from(final Long id, final LocalTime startAt) {
        return new Time(id, startAt);
    }

    public static Time of(final LocalTime startAt) {
        return new Time(null, startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
