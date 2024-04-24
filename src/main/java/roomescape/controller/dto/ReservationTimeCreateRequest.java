package roomescape.controller.dto;

import java.time.LocalTime;
import roomescape.entity.ReservationTime;

public class ReservationTimeCreateRequest {
    LocalTime startAt;

    public ReservationTimeCreateRequest() {
    }

    public ReservationTimeCreateRequest(LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTime toEntity() {
        return new ReservationTime(startAt);
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
