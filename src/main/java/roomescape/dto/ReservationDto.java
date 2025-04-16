package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.entity.Reservation;

public record ReservationDto(Long id, String name, String date, String time) {

    public static ReservationDto from(Reservation reservationInfo) {
        return new ReservationDto(
                reservationInfo.getId(),
                reservationInfo.getName(),
                reservationInfo.getDate().toString(),
                reservationInfo.getTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
