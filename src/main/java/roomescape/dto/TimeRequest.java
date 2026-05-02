package roomescape.dto;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;

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

    public ReservationTime toDomain() {
        return new ReservationTime(null, startAt);
    }
}
