package roomescape.reservation.controller.response;

import roomescape.reservation.domain.Reservation;
import roomescape.util.CustomDateTimeFormatter;

public record FindReservationResponse(Long id, String name, String date, FindTimeOfReservationsResponse time) {

    public static FindReservationResponse of(final Reservation reservation) {
        return new FindReservationResponse(
                reservation.getId(),
                reservation.getName().getValue(),
                CustomDateTimeFormatter.getFormattedDate(reservation.getDate().getValue()),
                FindTimeOfReservationsResponse.of(reservation.getReservationTime())
        );
    }
}
