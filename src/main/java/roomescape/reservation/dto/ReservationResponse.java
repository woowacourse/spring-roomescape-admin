package roomescape.reservation.dto;

import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationTime.domain.ReservationTime;

public record ReservationResponse(Long id, String name, LocalDate date, ReservationTime time) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.id(), reservation.name(), reservation.date(), reservation.time());
    }
}
