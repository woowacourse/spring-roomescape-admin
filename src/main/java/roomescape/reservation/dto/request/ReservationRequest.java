package roomescape.reservation.dto.request;

import java.time.LocalDate;
import roomescape.reservation.Reservation;
import roomescape.reservationTime.ReservationTime;

public record ReservationRequest(String name, LocalDate date, Long timeId) {
    public Reservation toDomain(Long id, ReservationTime time) {
        return new Reservation(id, name, date, time);
    }
}