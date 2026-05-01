package roomescape.dto;

import roomescape.domain.Time;

public class TimeRequest {
    private String startAt;

    public TimeRequest() {
    }

    public TimeRequest(String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }

    public Time toDomain() {
        return new Time(null, startAt);
    }
}
