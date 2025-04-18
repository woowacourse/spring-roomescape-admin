package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationResponse(long id, String name, String date, String time) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.getPersonName(),
                reservation.getDate().toString(),
                reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
