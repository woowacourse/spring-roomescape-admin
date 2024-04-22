package roomescape.controller;

import roomescape.domain.Reservation;
import roomescape.util.CustomDateTimeFormatter;

public record FindReservationResponse(Long id, String name, String date, String time) {

    public static FindReservationResponse of(final Reservation reservation) {
        return new FindReservationResponse(
                reservation.getId(),
                reservation.getName().getValue(),
                CustomDateTimeFormatter.getFormattedDate(reservation.getDate()),
                CustomDateTimeFormatter.getFormattedTime(reservation.getTime())
        );
    }
}
