package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record CreateReservationTimeDto(LocalTime startAt) {

    public ReservationTime toReservationTime() {
        return new ReservationTime(null, startAt);
    }
}
