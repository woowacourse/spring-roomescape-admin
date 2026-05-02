package roomescape.dto;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;

public class TimeResponse {
    private Long id;
    private LocalTime startAt;

    public TimeResponse(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static TimeResponse from(ReservationTime time) {
        return new TimeResponse(time.getId(), time.getStartAt());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
