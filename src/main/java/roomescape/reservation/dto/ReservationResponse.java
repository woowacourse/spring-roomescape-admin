package roomescape.reservation.dto;

import java.time.LocalDate;
import roomescape.reservation.entity.Reservation;

public record ReservationResponse(Long id, String name, LocalDate date,
                                  ReservationTimeResponse time) {

    public static ReservationResponse from(Reservation reservation) {
        ReservationTimeResponse reservationTimeResponse = ReservationTimeResponse.from(
                reservation.getReservationTime());

        return new ReservationResponse(reservation.getId(),
                reservation.getName(),
                reservation.getReservationDate(),
                reservationTimeResponse);
    }
}
