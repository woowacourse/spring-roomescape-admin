package roomescape.data.dto;

import java.time.LocalTime;

public class ReservationTimeRequest {
    private final LocalTime startAt;

    public ReservationTimeRequest() {
        startAt = LocalTime.now();
    }

    public ReservationTimeRequest(LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
