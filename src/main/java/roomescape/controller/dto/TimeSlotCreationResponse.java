package roomescape.controller.dto;

import java.time.LocalTime;
import roomescape.domain.TimeSlot;

public record TimeSlotCreationResponse(Long id, LocalTime startAt) {

    public static TimeSlotCreationResponse from(TimeSlot timeSlot) {
        return new TimeSlotCreationResponse(
                timeSlot.getId(),
                timeSlot.getTime()
        );
    }
}
