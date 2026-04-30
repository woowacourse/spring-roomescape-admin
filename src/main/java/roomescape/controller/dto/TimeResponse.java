package roomescape.controller.dto;

import roomescape.domain.ReservationTime;

public record TimeResponse(Long id, String startAt) {
    public static TimeResponse from(ReservationTime time) {
        return new TimeResponse(time.id(), time.startAt());
    }
}
