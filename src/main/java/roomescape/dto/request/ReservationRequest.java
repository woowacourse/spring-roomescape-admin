package roomescape.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(

        @NotNull
        LocalDate date,

        @NotNull
        String name,

        @NotNull
        LocalTime time
) {
}
