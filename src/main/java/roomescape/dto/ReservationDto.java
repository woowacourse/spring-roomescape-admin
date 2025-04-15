package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationDto(long id, String name, String date, String time) {
    public static ReservationDto from(Reservation reservation){
        return new ReservationDto(
            reservation.getId(),
            reservation.getPerson().getName(),
            reservation.getReservationTime().getDate().toString(),
            reservation.getReservationTime().getTime().format(DateTimeFormatter.ofPattern("mm:ss"))
        );
    }
}
