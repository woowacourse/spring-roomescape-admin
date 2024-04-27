package roomescape.dto;

import java.time.LocalTime;

public record CreateReservationTimeResponse(long id, LocalTime startAt) {
}
