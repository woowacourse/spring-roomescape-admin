package roomescape.controller.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeRequest(@NotNull LocalTime startAt) {

    public ReservationTime toEntity(final Long id) {
        return new ReservationTime(id, startAt);
    }
}
