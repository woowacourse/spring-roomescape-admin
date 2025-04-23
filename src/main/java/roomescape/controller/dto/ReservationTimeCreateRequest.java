package roomescape.controller.dto;

import jakarta.validation.constraints.NotNull;
import roomescape.entity.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeCreateRequest(@NotNull LocalTime startAt) {

    public ReservationTime toReservationTime() {
        return new ReservationTime(null, startAt);
    }
}
