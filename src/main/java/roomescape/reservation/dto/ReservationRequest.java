package roomescape.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ReservationRequest(
        @NotNull LocalDate date,
        @NotBlank String name,
        @NotNull Long timeId
) {
}
