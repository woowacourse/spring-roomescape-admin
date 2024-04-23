package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

public record ReservationResponse(Long id, String name, String date, ReservationTimeResponse time) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate().format(DateTimeFormatter.ISO_DATE),
            ReservationTimeResponse.from(reservation.getTime())
        );
    }

    public static ReservationResponse of(Long id, ReservationRequest reservationRequest, ReservationTime reservationTime) {
        return new ReservationResponse(
            id,
            reservationRequest.name(),
            reservationRequest.date(),
            ReservationTimeResponse.from(reservationTime)
        );
    }
}
