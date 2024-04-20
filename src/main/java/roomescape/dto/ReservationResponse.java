package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationResponse(
        long id,
        String name,
        String date,
        String time) {
    private static final String TIME_FORMAT = "HH:mm";

    public ReservationResponse(Reservation reservation) {
        this(reservation.getId(),
                reservation.getName(),
                reservation.getDate().format(DateTimeFormatter.ISO_DATE),
                reservation.getTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
    }
}
