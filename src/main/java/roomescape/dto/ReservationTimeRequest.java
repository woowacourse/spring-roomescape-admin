package roomescape.dto;

import java.time.LocalTime;

public class ReservationTimeRequest {
    private LocalTime startAt;

    public ReservationTimeRequest() {
    }

    public ReservationTimeRequest(LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
