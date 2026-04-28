package roomescape.reservation.dto.response;

import java.time.LocalDate;
import roomescape.reservation.Reservation;
import roomescape.reservationTime.ReservationTime;

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
