package roomescape.dto.reservationtime;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeRequest(
        LocalTime startAt
) {

    public ReservationTime toReservationTime() {
        return new ReservationTime(null, startAt);
    }
}
