package roomescape.dto.response;

import roomescape.business.domain.Reservation;

import java.time.LocalDate;

public record ReservationResponse(
        long id,
        String name,
        LocalDate date,
        ReservationTimeResponse time
) {
    public static ReservationResponse from(final Reservation reservation, final long reservationId, final long timeId) {
        return new ReservationResponse(
                reservationId,
                reservation.customerName(),
                reservation.date(),
                ReservationTimeResponse.from(reservation.reservationTime(), timeId)
        );
    }
}
