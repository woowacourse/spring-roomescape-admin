package roomescape.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        @NotNull @FutureOrPresent
        LocalDate date,
        @NotBlank @Size(max = 10)
        String name,
        @NotNull
        LocalTime time) {
}
