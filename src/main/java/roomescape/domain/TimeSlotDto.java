package roomescape.domain;

import java.time.LocalTime;

public record TimeSlotDto(LocalTime startAt) {
    public TimeSlot toEntity(Long id) {
        return new TimeSlot(id, startAt);
    }
}
