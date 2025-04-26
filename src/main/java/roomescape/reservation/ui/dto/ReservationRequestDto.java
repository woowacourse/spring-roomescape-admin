package roomescape.reservation.ui.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservationRequestDto(@NotBlank String name,
                                    @NotNull LocalDate date,
                                    @NotNull Long timeId) {

}
