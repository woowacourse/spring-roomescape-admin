package roomescape.reservationtime.controller.request;

import java.time.LocalTime;
import roomescape.reservationtime.domain.NewReservationTime;

public record CreateReservationTimeRequest(LocalTime startAt) {
    public NewReservationTime toDomain() {
        return new NewReservationTime(
                null,
                startAt);
    }
}
