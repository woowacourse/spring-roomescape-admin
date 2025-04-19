package roomescape;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(@NotBlank String name, @NotNull LocalDate date, @NotNull LocalTime time) {
}
