package roomescape.dto;

import roomescape.domain.ReservationTime;

public record ReservationTimeCreateRequest(String startAt) {

    public ReservationTime toReservationTime() {
        return new ReservationTime(startAt);
    }
}
