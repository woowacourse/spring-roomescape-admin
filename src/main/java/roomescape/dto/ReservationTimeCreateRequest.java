package roomescape.dto;

import jakarta.validation.constraints.NotNull;
import roomescape.entity.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeCreateRequest(
        @NotNull(message = "시간은 필수입니다.")
        LocalTime startAt
) {
    public ReservationTime toEntity() {
        return new ReservationTime(null, startAt);
    }
}
