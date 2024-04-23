package roomescape.dto;

import java.time.LocalTime;
import roomescape.model.ReservationTime;

public class ReservationTimeRequest {
    private static final Long TEMPORARY_ID = null;
    private LocalTime startAt;

    public ReservationTimeRequest() {
    }

    public ReservationTimeRequest(LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTime toEntity() {
        return new ReservationTime(TEMPORARY_ID, startAt);
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
