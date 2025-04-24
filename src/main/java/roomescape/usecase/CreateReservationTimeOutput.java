package roomescape.usecase;

import java.time.LocalTime;

public record CreateReservationTimeOutput(Long id, LocalTime startAt) {
}
