package roomescape.service.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeServiceResponse(
        Long id,
        LocalTime startAt
) {

        public static ReservationTimeServiceResponse from(ReservationTime reservationTime) {
                return new ReservationTimeServiceResponse(reservationTime.getId(), reservationTime.getStartAt());
        }
}
