package roomescape.reservation.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationSaveRequest(@NotEmpty String name,
                                     @JsonFormat(pattern = "yyyy-MM-dd") @NotNull LocalDate date,
                                     @JsonFormat(pattern = "HH:mm") @NotNull LocalTime time) {
}
