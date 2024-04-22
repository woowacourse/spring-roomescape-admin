package roomescape.controller.dto;

import roomescape.domain.ReservationTime;

public record TimeCreateRequest(String startAt) {

    public ReservationTime toReservationTime() {
        return new ReservationTime(startAt);
    }
}
