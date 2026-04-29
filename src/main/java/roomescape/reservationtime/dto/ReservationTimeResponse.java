package roomescape.reservationtime.dto;

import java.time.LocalTime;
import lombok.Builder;

@Builder
public record ReservationTimeResponse(Long id, LocalTime startAt) { }
