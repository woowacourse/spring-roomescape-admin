package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.model.Reservation;

public record ReservationResponse(Long id, String name, String date, String time) {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationResponse from(final Reservation reservation) {
        final String date = reservation.getDate().format(DateTimeFormatter.ISO_DATE);
        final String time = reservation.getTime().format(TIME_FORMATTER);
        return new ReservationResponse(reservation.getId(), reservation.getName(), date, time);
    }
}
