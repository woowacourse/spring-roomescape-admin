package roomescape.controller.dto;

import roomescape.domain.Reservation;
import roomescape.util.CustomDateTimeFormatter;

public record ReservationFindResponse(Long id, String name, String date, ReservationTimeFindResponse time) {

    public static ReservationFindResponse of(final Reservation reservation) {
        return new ReservationFindResponse(
                reservation.getId(),
                reservation.getName(),
                CustomDateTimeFormatter.getFormattedDate(reservation.getDate()),
                ReservationTimeFindResponse.of(reservation.getReservationTime())
        );
    }
}
