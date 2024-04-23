package roomescape.reservationtime.controller.request;

import java.time.LocalTime;
import roomescape.reservationtime.domain.ReservationTime;

public record CreateReservationTimeRequest(LocalTime startAt) {
    public ReservationTime toDomain() {
        return new ReservationTime(
                null,
                startAt);
    }
}
