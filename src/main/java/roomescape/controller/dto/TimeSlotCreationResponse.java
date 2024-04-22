package roomescape.controller.dto;

import roomescape.domain.TimeSlot;
import roomescape.utils.TimeFormatter;

public record TimeSlotCreationResponse(Long id, String startAt) {

    public static TimeSlotCreationResponse from(TimeSlot timeSlot) {
        return new TimeSlotCreationResponse(
                timeSlot.getId(),
                TimeFormatter.format(timeSlot.getTime())
        );
    }
}
