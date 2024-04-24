package roomescape.controller.dto;

import java.time.LocalTime;

public class CreateAvailableTimeResponse {
    Long id;
    LocalTime time;

    public CreateAvailableTimeResponse(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
