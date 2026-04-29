package roomescape.reservationtime.dto;

import java.time.LocalTime;
import lombok.Builder;

@Builder
public record ReservationTimeRequest (LocalTime startAt) { }
