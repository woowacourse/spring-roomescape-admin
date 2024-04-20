package roomescape.controller.dto;

import roomescape.domain.ReservationTime;
import roomescape.utils.TimeFormatter;

public record ReservationTimeResponse(Long id, String startAt) {

    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
                reservationTime.getId(),
                TimeFormatter.format(reservationTime.getTime())
        );
    }
}
