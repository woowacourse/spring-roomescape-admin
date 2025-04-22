package roomescape.dto;

import roomescape.entity.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeRequest(LocalTime startAt) {

    public ReservationTime toEntity() {
        return ReservationTime.of(startAt);
    }
}
