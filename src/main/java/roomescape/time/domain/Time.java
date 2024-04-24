package roomescape.time.domain;

import java.time.LocalTime;
import java.util.Objects;

public class Time {
    private Long id;
    private LocalTime startAt;

    public Time() {
    }

    public Time(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Time(final LocalTime startAt) {
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
