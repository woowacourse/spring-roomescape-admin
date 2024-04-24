package roomescape.controller.dto;

import java.time.LocalTime;
import roomescape.entity.GameTime;

public class GameTimeCreateRequest {
    private LocalTime startAt;

    public GameTimeCreateRequest() {
    }

    public GameTimeCreateRequest(LocalTime startAt) {
        this.startAt = startAt;
    }

    public GameTime toEntity() {
        return new GameTime(startAt);
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
