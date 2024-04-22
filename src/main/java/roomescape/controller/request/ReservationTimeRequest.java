package roomescape.controller.request;

import java.time.LocalTime;

public record ReservationTimeRequest(LocalTime startAt) {
    @Override
    public LocalTime startAt() {
        return startAt;
    }
}
