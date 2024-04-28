package roomescape.controller.dto;

import java.time.LocalTime;
import roomescape.entity.ReservationTime;

public class GameTimeCreateRequest {
    private LocalTime startAt;

    public GameTimeCreateRequest() {
    }

    public GameTimeCreateRequest(LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTime toEntity() {
        return new ReservationTime(startAt);
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
