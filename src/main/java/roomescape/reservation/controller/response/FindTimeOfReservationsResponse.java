package roomescape.reservation.controller.response;

import roomescape.reservationtime.domain.NewReservationTime;
import roomescape.util.CustomDateTimeFormatter;

public record FindTimeOfReservationsResponse(Long id, String startAt) {
    public static FindTimeOfReservationsResponse of(final NewReservationTime newReservationTime) {
        return new FindTimeOfReservationsResponse(
                newReservationTime.getId(),
                CustomDateTimeFormatter.getFormattedTime(newReservationTime.getTime())
        );
    }
}
