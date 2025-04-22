package roomescape.dto;

import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record AddReservationTimeDto(@FutureOrPresent LocalTime startAt) {

    public ReservationTime toEntity() {
        return new ReservationTime(null, startAt);
    }
}

