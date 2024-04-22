package roomescape.reservation.dto;

import java.time.LocalTime;
import roomescape.reservation.domain.TimeSlot;

public record TimeSlotResponse(long id, LocalTime startAt) {
    public static TimeSlotResponse from(TimeSlot timeSlot) {
        return new TimeSlotResponse(timeSlot.getId(), timeSlot.getStartAt());
    }
}
