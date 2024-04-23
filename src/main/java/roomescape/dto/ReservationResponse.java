package roomescape.dto;

import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(Long id, String name, LocalDate date, ReservationTime time) {

    public static ReservationResponse from(Reservation reservation, ReservationTime reservationTime) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservationTime
        );
    }
}
