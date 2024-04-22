package roomescape.controller.dto;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeCreateRequest(LocalTime startAt) {

    public ReservationTime toReservationTime(final Long id) {
        return new ReservationTime(id, startAt);
    }
}
