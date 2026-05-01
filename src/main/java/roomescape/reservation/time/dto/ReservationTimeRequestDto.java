package roomescape.reservation.time.dto;

import roomescape.reservation.time.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeRequestDto(
        LocalTime startAt
) {
    public ReservationTime toEntity() {
        return ReservationTime.of(null, startAt());
    }
}
