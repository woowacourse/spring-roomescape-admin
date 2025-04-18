package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationResponse(Long id, String name, LocalDate date, LocalTime time) {

    public static ReservationResponse fromReservation(Reservation reservation) {
        return new ReservationResponse(reservation.id(), reservation.name(), reservation.date(), reservation.time());
    }
}
