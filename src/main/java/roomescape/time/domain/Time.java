package roomescape.time.domain;

import java.time.LocalTime;
import java.util.Objects;

public class Time {
    private final Long id;
    private final LocalTime startAt;

    public Time(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
