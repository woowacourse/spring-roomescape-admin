package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

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

    public ReservationTime toTime(Long id) {
        return new ReservationTime(id, this.startAt);
    }
}
