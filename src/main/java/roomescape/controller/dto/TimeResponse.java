package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public class TimeResponse {
    private final long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private final LocalTime startAt;

    public TimeResponse(long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
