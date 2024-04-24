package roomescape.controller.dto;

import java.time.LocalTime;

public class CreateAvailableTimeRequest {
    LocalTime time;

    public CreateAvailableTimeRequest() {
    }

    public CreateAvailableTimeRequest(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}
