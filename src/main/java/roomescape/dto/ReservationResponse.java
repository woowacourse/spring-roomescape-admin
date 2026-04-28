package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.entity.Reservation;

public record ReservationResponse(Long id, String name, String date, String time) {

    public static ReservationResponse toDto(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
