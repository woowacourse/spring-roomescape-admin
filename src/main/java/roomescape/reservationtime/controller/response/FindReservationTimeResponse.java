package roomescape.reservationtime.controller.response;

import roomescape.reservationtime.domain.ReservationTime;
import roomescape.util.CustomDateTimeFormatter;

public record FindReservationTimeResponse(Long id, String startAt) {
    public static FindReservationTimeResponse of(final ReservationTime reservationTime) {
        return new FindReservationTimeResponse(
                reservationTime.getId(),
                CustomDateTimeFormatter.getFormattedTime(reservationTime.getTime()));
    }
}
