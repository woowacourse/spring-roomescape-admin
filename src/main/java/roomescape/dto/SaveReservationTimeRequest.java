package roomescape.dto;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;

public record SaveReservationTimeRequest(LocalTime startAt) {
    public ReservationTime toReservationTime() {
        return new ReservationTime(startAt);
    }
}
