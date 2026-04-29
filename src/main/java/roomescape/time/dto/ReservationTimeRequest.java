package roomescape.time.dto;

import jakarta.validation.constraints.NotNull;

public record ReservationTimeRequest(
        @NotNull(message = "시작 시간은 필수입니다.")
        String startAt
) {
}
