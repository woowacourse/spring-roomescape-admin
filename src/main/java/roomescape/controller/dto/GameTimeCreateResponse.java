package roomescape.controller.dto;

import java.time.LocalTime;
import roomescape.entity.ReservationTime;

public class GameTimeCreateResponse {
    private final Long id;
    private final LocalTime startAt;

    public GameTimeCreateResponse(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static GameTimeCreateResponse from(ReservationTime reservationTime) {
        return new GameTimeCreateResponse(reservationTime.getId(), reservationTime.getStartAt());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
