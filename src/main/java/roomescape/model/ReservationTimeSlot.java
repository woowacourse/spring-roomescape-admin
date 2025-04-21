package roomescape.model;

import java.time.LocalTime;

public record ReservationTimeSlot(
    Long id,
    LocalTime startAt
) {

}
