package roomescape.controller;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record FindReservationResponse(Long id, String name, String date, String time) {

    public static FindReservationResponse of(final Reservation reservation) {
        return new FindReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                reservation.getDateTime().format(DateTimeFormatter.ofPattern("hh:mm")));
    }
}
