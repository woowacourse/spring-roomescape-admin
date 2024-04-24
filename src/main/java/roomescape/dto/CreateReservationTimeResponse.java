package roomescape.dto;

import java.time.LocalTime;

public record CreateReservationTimeResponse(int id, LocalTime startAt) {
}
