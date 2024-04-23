package roomescape.data.dto.request;

import java.time.LocalTime;

public class ReservationTimeRequest {
    private final LocalTime startAt;

    public ReservationTimeRequest() {
        startAt = LocalTime.now();
    }

    public ReservationTimeRequest(final LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
