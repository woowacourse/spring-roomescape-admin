package roomescape.dto;

import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {

    public static Reservation from(ReservationRequest reservationRequest) {
        return new Reservation(
                null,
                reservationRequest.name,
                reservationRequest.date,
                reservationRequest.time
        );
    }
}
