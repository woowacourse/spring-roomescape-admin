package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.model.Reservation;

public record ReservationResponse(Long id, String name, String date, String time) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate().format(DateTimeFormatter.ISO_DATE),
            reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
