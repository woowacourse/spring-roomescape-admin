package roomescape.core.dto;

import java.time.LocalTime;
import roomescape.core.model.ReservationTime;

public class ReservationTimeRequest {
    private static final Long TEMPORARY_ID = null;
    private LocalTime startAt;

    public ReservationTimeRequest() {
    }

    public ReservationTimeRequest(LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTime toEntity() {
        return new ReservationTime(startAt);
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
