package roomescape;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record ReservationTimeSaveDto(@NotNull LocalTime startAt) {
}
