package roomescape.dto;

import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

public record ReservationRequest(String name, String date, Long timeId) {

    public static Reservation of(ReservationRequest reservationRequest, ReservationTime reservationTime) {
        return new Reservation(
            null,
            reservationRequest.name,
            reservationRequest.date,
            reservationTime
        );
    }
}
