package roomescape.dto;

import roomescape.domain.ReservationTime;

public class TimeResponse {

    private Long id;
    private String startAt;

    private TimeResponse(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static TimeResponse from(ReservationTime time) {
        return new TimeResponse(time.getId(), time.getStartAt());
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
