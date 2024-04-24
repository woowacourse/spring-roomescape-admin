package roomescape.domain;

import java.time.LocalTime;

public class Time {
    private Long id;
    private LocalTime startAt;

    public Time() {
    }

    public Time(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Time(Long id) {
        this.id = id;
    }

    public Time(LocalTime startAt) {
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
