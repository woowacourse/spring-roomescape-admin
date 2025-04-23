package roomescape.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record ReservationTimeCreateRequest(@NotNull LocalTime startAt) {
}
