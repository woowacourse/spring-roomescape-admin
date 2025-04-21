package roomescape.time.controller.request;

import java.time.LocalTime;
import roomescape.time.domain.ReservationTime;

public record ReservationTimeCreateRequest(String startAt) {

    public ReservationTime to() {
        return new ReservationTime(LocalTime.parse(startAt));
    }
}
