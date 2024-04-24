package roomescape.controller.dto;

import java.time.LocalTime;
import roomescape.entity.ReservationTime;

public class CreateAvailableTimeResponse {
    Long id;
    LocalTime startAt;

    public CreateAvailableTimeResponse(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static CreateAvailableTimeResponse from(ReservationTime reservationTime) {
        return new CreateAvailableTimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
