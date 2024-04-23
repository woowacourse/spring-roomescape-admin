package roomescape.service.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeServiceRequest(
        LocalTime startAt
) {

        public ReservationTime toReservationTime() {
                return new ReservationTime(startAt);
        }
}
