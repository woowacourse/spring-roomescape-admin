package roomescape.reservationtime.controller.request;

import java.time.LocalTime;
import roomescape.reservation.domain.ReservationTime;

public record CreateReservationTimeRequest(LocalTime startAt) {
    public ReservationTime toDomain() {
        return new ReservationTime(startAt);
    }
}
