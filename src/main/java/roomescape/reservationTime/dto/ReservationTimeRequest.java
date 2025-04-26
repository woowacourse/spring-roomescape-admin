package roomescape.reservationTime.dto;

import java.time.LocalTime;
import roomescape.reservationTime.domain.ReservationTime;

public record ReservationTimeRequest(String startAt) {
    public ReservationTime createReservationTime() {
        return new ReservationTime(null, LocalTime.parse(startAt));
    }
}
