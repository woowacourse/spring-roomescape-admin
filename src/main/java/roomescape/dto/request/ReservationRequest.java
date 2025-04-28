package roomescape.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservationRequest(
        @NotNull LocalDate date,
        @NotNull String name,
        @NotNull Long timeId) {
}
