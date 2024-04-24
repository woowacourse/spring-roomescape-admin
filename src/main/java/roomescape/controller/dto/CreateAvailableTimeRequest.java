package roomescape.controller.dto;

import java.time.LocalTime;
import roomescape.entity.ReservationTime;

public class CreateAvailableTimeRequest {
    LocalTime startAt;

    public CreateAvailableTimeRequest() {
    }

    public CreateAvailableTimeRequest(LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTime toEntity() {
        return new ReservationTime(startAt);
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
