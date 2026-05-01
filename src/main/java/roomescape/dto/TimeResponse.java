package roomescape.dto;

import roomescape.domain.Time;

public class TimeResponse {
    private Long id;
    private String startAt;

    public TimeResponse(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static TimeResponse from(Time time) {
        return new TimeResponse(time.getId(), time.getStartAt());
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
