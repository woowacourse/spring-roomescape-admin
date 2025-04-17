package roomescape;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(@NotBlank String name, @NotNull @Future LocalDate date, @Future @NotNull LocalTime time) {
}
