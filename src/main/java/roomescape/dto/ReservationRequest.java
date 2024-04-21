package roomescape.dto;

import roomescape.model.Reservation;

public record ReservationRequest(String name, String date, String time) {

    public static Reservation from(ReservationRequest reservationRequest) {
        return new Reservation(
                null,
                reservationRequest.name,
                reservationRequest.date,
                reservationRequest.time
        );
    }
}
