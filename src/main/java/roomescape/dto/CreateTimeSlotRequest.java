package roomescape.dto;

import java.time.LocalTime;
import roomescape.model.TimeSlot;

public record CreateTimeSlotRequest(
    LocalTime startAt
) {

    public TimeSlot toEntity(final long id) {
        return new TimeSlot(id, startAt);
    }
}
