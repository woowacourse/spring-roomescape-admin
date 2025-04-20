package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        String time
) {

    public ReservationResponse(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getName(),
                reservation.formatDateTime(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                reservation.formatDateTime(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
