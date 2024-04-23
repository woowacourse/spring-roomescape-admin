package roomescape.reservationtime.controller.response;

import roomescape.reservationtime.domain.NewReservationTime;
import roomescape.util.CustomDateTimeFormatter;

public record FindReservationTimeResponse(Long id, String startAt) {
    public static FindReservationTimeResponse of(final NewReservationTime newReservationTime) {
        return new FindReservationTimeResponse(
                newReservationTime.getId(),
                CustomDateTimeFormatter.getFormattedTime(newReservationTime.getTime()));
    }
}
