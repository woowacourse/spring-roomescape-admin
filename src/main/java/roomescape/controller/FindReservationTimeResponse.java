package roomescape.controller;

import roomescape.domain.ReservationTime;
import roomescape.util.CustomDateTimeFormatter;

public record FindReservationTimeResponse(Long id, String startedAt) {
    public static FindReservationTimeResponse of(ReservationTime reservationTime) {
        return new FindReservationTimeResponse(
                reservationTime.getId(),
                CustomDateTimeFormatter.getFormattedTime(reservationTime.getStartAt())
        );
    }
}
