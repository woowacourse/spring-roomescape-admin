package roomescape.controller.dto;

import java.time.LocalTime;

public class ReservationAvailableTimeRequest {
    LocalTime time;

    public ReservationAvailableTimeRequest() {
    }

    public ReservationAvailableTimeRequest(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}
