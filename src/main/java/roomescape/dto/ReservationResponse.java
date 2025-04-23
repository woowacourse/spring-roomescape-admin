package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.model.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        ReservationTimeResponse reservationTimeResponse
) {
    public static ReservationResponse fromEntity(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                ReservationTimeResponse.fromEntity(reservation.getReservationTime())
        );
    }
}
