package roomescape.controller.dto;

import roomescape.domain.Reservation;
import roomescape.util.CustomDateTimeFormatter;

public record ReservationCreateResponse(Long id, String name, String date, String time) {

    public static ReservationCreateResponse of(final Reservation reservation) {
        return new ReservationCreateResponse(
                reservation.getId(),
                reservation.getName(),
                CustomDateTimeFormatter.getFormattedDate(reservation.getDate()),
                CustomDateTimeFormatter.getFormattedTime(reservation.getTime())
        );
    }
}
