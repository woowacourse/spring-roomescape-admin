package roomescape.domain;

import java.time.LocalTime;

public class Time {
    private Long id;
    private LocalTime startAt;

    public Time(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Time(final LocalTime startAt) {
        this.startAt = startAt;
    }

    public Time() {
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
