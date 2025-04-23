package roomescape.time.domain;

import java.time.LocalTime;

public class Time {
    private final Long id;
    private final LocalTime localTime;

    public Time(final Long id, final LocalTime localTime) {
        this.id = id;
        this.localTime = localTime;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }
}
