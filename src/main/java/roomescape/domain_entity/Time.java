package roomescape.domain_entity;

import java.time.LocalTime;

public class Time {
    private Id id;
    private LocalTime startAt;

    public Time() {
    }

    public Time(Id id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Id getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
