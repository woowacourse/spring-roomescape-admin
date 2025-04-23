package roomescape.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

public record ReservationTimeRequest(
    Long id,

    @NotNull(message = "[ERROR] 시간은 반드시 필요합니다.")
    LocalTime startAt
) {
}
