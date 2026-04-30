package roomescape.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReservationTimeResponse(
        Long id,
        @NotBlank
        String startAt
) {
}
