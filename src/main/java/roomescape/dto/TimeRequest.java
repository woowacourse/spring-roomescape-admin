package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.Time;

public class TimeRequest {
    private LocalTime startAt;

    public TimeRequest() {
    }

    public TimeRequest(LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public Time toTime(Long id) {
        return new Time(id, this.startAt);
    }
}
