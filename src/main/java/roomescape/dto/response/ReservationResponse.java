package roomescape.dto.response;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationResponse(Long id, String name, String date, String time) {

    public static ReservationResponse of(Long id, Reservation reservation) {
        return new ReservationResponse(
                id,
                reservation.getName(),
                reservation.getDate().format(DateTimeFormatter.ISO_DATE),
                reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
