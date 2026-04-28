package roomescape.reservationTime.dto;

import java.time.LocalTime;
import roomescape.reservationTime.ReservationTime;

public record ReservationTimeRequest(LocalTime startAt) {
    public ReservationTime toDomain(Long id) {
        return new ReservationTime(id, startAt);
    }
}
