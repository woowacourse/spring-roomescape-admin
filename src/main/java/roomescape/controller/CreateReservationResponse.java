package roomescape.controller;

import roomescape.domain.Reservation;
import roomescape.util.CustomDateTimeFormatter;

public record CreateReservationResponse(Long id, String name, String date, String time) {

    public static CreateReservationResponse of(final Reservation reservation) {
        return new CreateReservationResponse(
                reservation.getId(),
                reservation.getName(),
                CustomDateTimeFormatter.getFormattedDate(reservation.getDateTime()),
                CustomDateTimeFormatter.getFormattedTime(reservation.getDateTime())
        );
    }
}
