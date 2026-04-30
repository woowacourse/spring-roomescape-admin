package roomescape.dto;

import jakarta.validation.constraints.NotBlank;

public record ReservationTimeRequest(
        @NotBlank
        String startAt
) {
}
