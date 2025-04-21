package roomescape;

import java.time.LocalTime;

public record CreateTimeSlotRequest(
    LocalTime startAt
) {

    public ReservationTimeSlot toEntity(final long id) {
        return new ReservationTimeSlot(id, startAt);
    }
}
