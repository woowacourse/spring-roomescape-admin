package roomescape.dto;

import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.time.LocalDate;

public record ReservationRequest(String name, LocalDate date, Long timeId) {

    public Reservation toEntity(ReservationTime reservationTime) {
        return Reservation.of(name, date, reservationTime);
    }
}
