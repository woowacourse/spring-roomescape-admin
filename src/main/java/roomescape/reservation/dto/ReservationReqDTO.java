package roomescape.reservation.dto;

import roomescape.reservation.model.Reservation;
import roomescape.reservation.model.ReservationTime;

import java.time.LocalDate;

public record ReservationReqDTO(String name, LocalDate date, Long timeId) {

    public Reservation toEntityWith(ReservationTime time) {
        return new Reservation(name, date, time);
    }
}
