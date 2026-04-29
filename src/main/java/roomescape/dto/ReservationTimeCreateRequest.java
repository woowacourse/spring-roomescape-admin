package roomescape.dto;

import roomescape.entity.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeCreateRequest(
        LocalTime startAt
) {
    public ReservationTime toEntity() {
        return new ReservationTime(null, startAt);
    }
}
