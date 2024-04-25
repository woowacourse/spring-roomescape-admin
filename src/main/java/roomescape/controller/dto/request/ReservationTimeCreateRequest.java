package roomescape.controller.dto.request;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeCreateRequest(LocalTime startAt){

    public ReservationTime toReservationTime() {
        return new ReservationTime(null, startAt);
    }
}
