package roomescape;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ReservationSaveDto(
        @NotBlank @Size(max = 85) String name,
        @NotBlank LocalDate date,
        Long timeId) {
}
