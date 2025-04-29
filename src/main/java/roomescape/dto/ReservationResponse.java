package roomescape.dto;

import roomescape.reservation.Reservation;

import java.time.LocalDate;

public record ReservationResponse(Long id, String name, LocalDate date, ReservationTimeResponse time) {
    public static ReservationResponse of(Reservation reservation, ReservationTimeResponse reservationTimeResponse) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), reservationTimeResponse);
    };
}
