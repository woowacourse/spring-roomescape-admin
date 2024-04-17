package roomescape.dto.response;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationResponse(Long id, String name, String date, String time) {

    public static ReservationResponse fromEntity(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().format(DateTimeFormatter.ISO_DATE),
                reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}
