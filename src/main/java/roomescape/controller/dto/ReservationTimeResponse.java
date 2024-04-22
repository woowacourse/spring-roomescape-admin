package roomescape.controller.dto;

import roomescape.domain.ReservationTime;
import java.time.LocalTime;

public class ReservationTimeResponse {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTimeResponse(final ReservationTime reservationTime) {
        this.id = reservationTime.getId();
        this.startAt = reservationTime.getStartAt();
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
