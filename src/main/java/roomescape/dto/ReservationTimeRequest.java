package roomescape.dto;

import roomescape.domain.ReservationTime;

public class ReservationTimeRequest {

    private String startAt;

    private ReservationTimeRequest() {
    }

    public ReservationTimeRequest(String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }

    public ReservationTime toEntity() {
        return new ReservationTime(null, startAt);
    }
}
