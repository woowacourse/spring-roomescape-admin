package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public class TimeResponse {
    private final Long id;
    private final LocalTime startAt;

    public TimeResponse(ReservationTime reservationTime) {
        this(reservationTime.getId(), reservationTime.getStartAt());
    }

    public TimeResponse(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
