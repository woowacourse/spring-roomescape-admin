package roomescape.dto;

import java.time.LocalTime;
import roomescape.ReservationTimeSlot;

public record CreateTimeSlotRequest(
    LocalTime startAt
) {

    public ReservationTimeSlot toEntity(final long id) {
        return new ReservationTimeSlot(id, startAt);
    }
}
