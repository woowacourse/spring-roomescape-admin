package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationResponseDto(long id, String name, String date, String time) {

    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
            reservation.getId(),
            reservation.getPerson().getName(),
            reservation.getReservationTime().getDate().toString(),
            reservation.getReservationTime().getTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
