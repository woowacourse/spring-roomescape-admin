package roomescape.core.service.dto;

import java.time.LocalTime;
import roomescape.core.domain.ReservationTime;

public record ReservationTimeServiceRequest(
        LocalTime startAt
) {

        public ReservationTime toReservationTime() {
                return new ReservationTime(startAt);
        }
}
