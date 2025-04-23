package roomescape.reservation.dto;

import java.time.LocalDate;
import roomescape.reservation.Reservation;

public record ReservationDto(String name, LocalDate date, Long timeId) {
    public Reservation createReservation() {
        return new Reservation(name, date, timeId);
    }
}
