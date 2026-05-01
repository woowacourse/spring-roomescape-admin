package roomescape.dto;

import roomescape.domain.ReservationTime;

public class ReservationTimeResponse {

    private final Long id;
    private final String startAt;

    private ReservationTimeResponse(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeResponse from(ReservationTime time) {
        return new ReservationTimeResponse(time.getId(), time.getStartAt());
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
