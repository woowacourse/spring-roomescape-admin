package roomescape.dto;

import roomescape.entity.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeCreateRequest(
        LocalTime startAt
) {
    public ReservationTime toEntity(Long id) {
        return new ReservationTime(id, startAt);
    }
}
