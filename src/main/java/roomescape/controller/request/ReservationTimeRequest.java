package roomescape.controller.request;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeRequest(LocalTime startAt) {

    public ReservationTime toEntity(final Long id) {
        return new ReservationTime(id, startAt);
    }
}
