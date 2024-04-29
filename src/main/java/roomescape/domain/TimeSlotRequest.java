package roomescape.domain;

import java.time.LocalTime;

public record TimeSlotRequest(LocalTime startAt) {
    public TimeSlot toEntity(Long id) {
        return new TimeSlot(id, startAt);
    }
}
