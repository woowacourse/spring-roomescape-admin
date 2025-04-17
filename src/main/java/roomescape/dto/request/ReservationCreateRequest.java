package roomescape.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(@NotBlank String name, @FutureOrPresent LocalDate date,
                                       @FutureOrPresent LocalTime time) {
}
