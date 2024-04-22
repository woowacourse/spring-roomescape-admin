package roomescape.dto;

import roomescape.model.ReservationTime;

public record ReservationTimeRequest(String startAt) {

    public static ReservationTime from(ReservationTimeRequest reservationTimeRequest) {
        return new ReservationTime(null, reservationTimeRequest.startAt);
    }
}
