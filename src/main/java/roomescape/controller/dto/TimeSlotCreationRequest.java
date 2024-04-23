package roomescape.controller.dto;

import roomescape.domain.TimeSlot;

public record TimeSlotCreationRequest(String startAt) {

    public TimeSlot toEntity() {
        return new TimeSlot(startAt);
    }
}
