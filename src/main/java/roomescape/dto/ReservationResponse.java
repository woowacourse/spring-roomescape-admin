package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.entity.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        String time
) {

    public static ReservationResponse from(final Reservation reservationInfo) {
        return new ReservationResponse(
                reservationInfo.getId(),
                reservationInfo.getName(),
                reservationInfo.getDate().toString(),
                reservationInfo.getTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
