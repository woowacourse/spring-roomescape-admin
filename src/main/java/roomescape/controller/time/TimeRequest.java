package roomescape.controller.time;

import roomescape.entity.ReservationTime;

import java.time.LocalTime;

public record TimeRequest(LocalTime startAt) {

    public ReservationTime toDomain() {
        return new ReservationTime(null, startAt);
    }
}
