package roomescape.core.service.request;

import roomescape.core.domain.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeRequestDto(LocalTime startAt) {
    public ReservationTime toEntity() {
        return new ReservationTime(startAt);
    }
}
