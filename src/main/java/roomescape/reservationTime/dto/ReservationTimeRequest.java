package roomescape.reservationTime.dto;

import roomescape.reservationTime.domain.ReservationTime;

public record ReservationTimeRequest(String startAt) {
    public ReservationTime createReservationTime() {
        return new ReservationTime(null, startAt);
    }
}
