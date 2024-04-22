package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;

public record ReservationResponse(
        long id,
        String name,
        LocalDate date,
        ReservationTimeResponse time
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName().value(),
                reservation.getDate(),
                ReservationTimeResponse.from(reservation.getTime()));
    }
}
