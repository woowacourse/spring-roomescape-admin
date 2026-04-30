package roomescape.reservation.time.dto;

import roomescape.reservation.time.ReservationTime;

public record ReservationTimeRequestDto(
        String startAt
) {
    public ReservationTime toEntity() {
        return ReservationTime.of(null, startAt());
    }
}
