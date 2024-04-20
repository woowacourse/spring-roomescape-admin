package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.model.Reservation;

public record ReservationResponse(Long id, String name, String date, ReservationTimeResponse time) {

    public static ReservationResponse from(final Reservation reservation) {
        final String date = reservation.getDate().format(DateTimeFormatter.ISO_DATE);
        final ReservationTimeResponse time = ReservationTimeResponse.from(reservation.getTime());
        return new ReservationResponse(reservation.getId(), reservation.getName(), date, time);
    }
}
