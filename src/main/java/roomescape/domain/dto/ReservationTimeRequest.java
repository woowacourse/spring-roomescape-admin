package roomescape.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record ReservationTimeRequest(
        @NotBlank
        String startAt
) {
}
