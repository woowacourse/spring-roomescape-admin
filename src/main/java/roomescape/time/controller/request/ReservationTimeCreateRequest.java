package roomescape.time.controller.request;

import java.time.LocalTime;
import roomescape.time.domain.ReservationTime;

public record ReservationTimeCreateRequest(LocalTime startAt) {

    public ReservationTime to() {
        return new ReservationTime(startAt);
    }
}
