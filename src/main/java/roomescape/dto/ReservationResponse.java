package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationResponse(Long id, String name, String date, ReservationTimeResponse time) {

    public static ReservationResponse from(final Reservation reservation) {
        final ReservationTime time = reservation.getTime();
        final ReservationTimeResponse reservationTimeResponse = new ReservationTimeResponse(time.getId(),
                time.getStartAt().toString());
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate().toString(),
                reservationTimeResponse);
    }
}
