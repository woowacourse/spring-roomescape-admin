package roomescape.reservation.dto.response;

import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.response.ReservationTimeResponse;

public record ReservationResponse(
        long id,
        String name,
        LocalDate date,
        ReservationTimeResponse time
) {
    public static ReservationResponse from(long id, long reservationTimeId, Reservation reservation,
                                           ReservationTime reservationTime) {
        return new ReservationResponse(id, reservation.getName(), reservation.getDate(),
                ReservationTimeResponse.from(reservationTimeId, reservationTime)
        );
    }
}
