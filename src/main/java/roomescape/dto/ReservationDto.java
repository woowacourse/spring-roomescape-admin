package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.Reservation;

public record ReservationDto(Long id, String name, String date, String time) {

    public static ReservationDto from(Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().toString(),
                reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
