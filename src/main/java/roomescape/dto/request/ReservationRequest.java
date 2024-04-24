package roomescape.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReservationRequest(
        @NotNull String name,
        @NotNull @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String date,
        long timeId) {
}
