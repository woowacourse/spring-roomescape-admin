package roomescape.dto;

import java.time.format.DateTimeFormatter;

import roomescape.model.Name;
import roomescape.model.Reservation;

public record ReservationResponse(Long id, String name, String date, ReservationTimeResponse time) {

    public static ReservationResponse from(final Reservation reservation) {
        final String date = reservation.getDate().format(DateTimeFormatter.ISO_DATE);
        final ReservationTimeResponse time = ReservationTimeResponse.from(reservation.getTime());
        final Name name = reservation.getName();
        return new ReservationResponse(reservation.getId(), name.getValue(), date, time);
    }
}
