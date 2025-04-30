package roomescape.dto;

import roomescape.model.ReservationTime;

public record ReservationTimeRequest(String startAt) {

    public ReservationTime toReservationTime() {
        return new ReservationTime(startAt);
    }
}
