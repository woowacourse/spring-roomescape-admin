package roomescape.dto.request;

import jakarta.validation.constraints.NotNull;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeRequest(@NotNull LocalTime startAt) {

    public ReservationTime toReservationTime() {
        return new ReservationTime(startAt);
    }
}
