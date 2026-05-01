package roomescape.domain.reservation.dto;

import java.time.LocalDate;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;

public record ReservationResponse(
    Long id,
    String name,
    LocalDate date,
    ReservationTimePayload time
) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate(),
            ReservationTimePayload.from(reservation.getTime())
        );
    }

    public record ReservationTimePayload(
        Long id,
        String startAt
    ) {

        public static ReservationTimePayload from(ReservationTime reservationTime) {
            return new ReservationTimePayload(reservationTime.getId(), reservationTime.getStartAt());
        }
    }
}
