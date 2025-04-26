package roomescape.dto.response;

import roomescape.domain.Reservation;

import java.time.LocalDate;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeResponse time) {

    public static ReservationResponse of(final Reservation reservation, final ReservationTimeResponse timeResponse) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), timeResponse);
    }
}
