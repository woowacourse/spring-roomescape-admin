package roomescape.dto;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeRequest(LocalTime startAt) {

    public static ReservationTime toReservationTime(ReservationTimeRequest reservationTimeRequest) {
        return new ReservationTime(
                null,
                reservationTimeRequest.startAt()
        );
    }
}
