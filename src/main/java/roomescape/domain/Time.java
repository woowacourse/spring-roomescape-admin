package roomescape.domain;

import java.time.LocalTime;
import roomescape.dto.TimeRequest;

public class Time {

    private final Long id;
    private final LocalTime startAt;

    public Time(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Time(TimeRequest timeRequest) {
        this(null, timeRequest.startAt());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
