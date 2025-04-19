package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationResponseDto(long id, String name, String date, String time) {

    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
            reservation.getId(),
            reservation.getPersonName(),
            reservation.getDate().toString(),
            reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
