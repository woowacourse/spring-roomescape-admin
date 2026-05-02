package roomescape.domain.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ReservationRequestDTO(@NotBlank String name, @NotNull LocalDate date, @NotNull Long timeId) {
}
