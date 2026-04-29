package roomescape.reservation.presentation.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationFindResponse(@NotNull Long id,
                                      @NotEmpty String name,
                                      @NotNull LocalDate date,
                                      @NotNull LocalTime time) {
}
