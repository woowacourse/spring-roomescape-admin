package roomescape;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalTime;

public record ReservationTimeSaveDto(@NotBlank LocalTime startAt) {
}
