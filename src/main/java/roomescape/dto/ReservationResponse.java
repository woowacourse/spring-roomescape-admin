package roomescape.dto;

import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

import java.time.LocalDate;

public record ReservationResponse(Long id, String name, LocalDate date, ReservationTime time) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
