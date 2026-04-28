package roomescape.reservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        @NotBlank
        @Size(max = 10)
        String name,
        LocalDate date,
        LocalTime time
) {
}
