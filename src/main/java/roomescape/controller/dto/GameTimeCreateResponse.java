package roomescape.controller.dto;

import java.time.LocalTime;
import roomescape.entity.GameTime;

public class GameTimeCreateResponse {
    private final Long id;
    private final LocalTime startAt;

    public GameTimeCreateResponse(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static GameTimeCreateResponse from(GameTime gameTime) {
        return new GameTimeCreateResponse(gameTime.getId(), gameTime.getStartAt());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
