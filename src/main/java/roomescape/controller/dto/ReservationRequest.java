package roomescape.controller.dto;

import roomescape.domain.Reservation;
import roomescape.domain.TimeSlot;

public record ReservationRequest(String name, String date, Long timeSlotId) {

    public Reservation toEntity(TimeSlot timeSlot) {
        return new Reservation(name, date, timeSlot);
    }
}
