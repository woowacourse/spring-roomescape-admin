package roomescape.dto;

import roomescape.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(String name,
                                 LocalDate date,
                                 LocalTime time) {

    public static ReservationRequest from(Reservation reservation) {
        return new ReservationRequest(reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
