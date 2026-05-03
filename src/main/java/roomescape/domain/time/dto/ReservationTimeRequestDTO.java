package roomescape.domain.time.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record ReservationTimeRequestDTO(@NotNull LocalTime startAt) {
}
