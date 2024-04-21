package roomescape.controller.dto;

import roomescape.domain.ReservationTime;
import roomescape.util.CustomDateTimeFormatter;

public record ReservationTimeFindResponse(Long id, String startAt) {
    public static ReservationTimeFindResponse of(ReservationTime reservationTime) {
        return new ReservationTimeFindResponse(
                reservationTime.getId(),
                CustomDateTimeFormatter.getFormattedTime(reservationTime.getStartAt())
        );
    }
}
