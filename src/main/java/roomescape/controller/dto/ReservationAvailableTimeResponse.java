package roomescape.controller.dto;

import java.time.LocalTime;

public class ReservationAvailableTimeResponse {
    Long id;
    LocalTime time;

    public ReservationAvailableTimeResponse(Long id, LocalTime time) {
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
