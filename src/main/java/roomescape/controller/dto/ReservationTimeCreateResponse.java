package roomescape.controller.dto;

import java.time.LocalTime;
import roomescape.entity.ReservationTime;

public class ReservationTimeCreateResponse {
    Long id;
    LocalTime startAt;

    public ReservationTimeCreateResponse(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeCreateResponse from(ReservationTime reservationTime) {
        return new ReservationTimeCreateResponse(reservationTime.getId(), reservationTime.getStartAt());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
