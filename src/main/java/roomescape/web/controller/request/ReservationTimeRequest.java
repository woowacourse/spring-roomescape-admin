package roomescape.web.controller.request;

import roomescape.core.domain.ReservationTime;
import roomescape.core.service.request.ReservationTimeRequestDto;

import java.time.LocalTime;
import java.util.Objects;

public record ReservationTimeRequest(LocalTime startAt) {
    public ReservationTimeRequest {
        Objects.requireNonNull(startAt);
    }

    public ReservationTime toEntity() {
        return new ReservationTime(startAt);
    }

    public ReservationTimeRequestDto toDto() {
        return new ReservationTimeRequestDto(startAt);
    }
}
