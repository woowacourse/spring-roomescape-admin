package roomescape.dto;

import roomescape.domain.reservation.Name;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDate;
import roomescape.domain.reservation.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequestDto (String name, LocalDate date, LocalTime time) {

    public Reservation toEntity(){
        return new Reservation(new Name(name), new ReservationDate(date), new ReservationTime(time));
    }
}
