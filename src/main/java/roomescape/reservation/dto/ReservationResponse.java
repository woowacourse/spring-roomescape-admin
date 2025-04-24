package roomescape.reservation.dto;

import java.time.LocalDate;
import roomescape.reservation.Reservation;
import roomescape.reservationTime.ReservationTime;

public record ReservationResponse(Long id, String name, LocalDate date, ReservationTime time) {
    public static ReservationResponse of(Reservation reservation) {
        return new ReservationResponse(reservation.id(), reservation.name(), reservation.date(), reservation.time());
    }

    public static ReservationResponse of(Reservation reservation, ReservationTime reservationTime) {
        return new ReservationResponse(reservation.id(), reservation.name(), reservation.date(), reservationTime);
    }
}
