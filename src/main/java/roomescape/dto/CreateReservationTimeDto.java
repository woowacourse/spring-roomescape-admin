package roomescape.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record CreateReservationTimeDto(@NotNull LocalTime startAt) {

    public ReservationTime toReservationTime() {
        return new ReservationTime(null, startAt);
    }
}
