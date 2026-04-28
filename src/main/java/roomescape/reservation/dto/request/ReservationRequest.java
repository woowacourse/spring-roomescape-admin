package roomescape.reservation.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservation.Reservation;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {
    public Reservation toDomain(Long id) {
        return new Reservation(id, name, date, time);
    }
}
