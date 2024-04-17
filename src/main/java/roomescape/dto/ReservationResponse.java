package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        String time
) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                reservation.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}
