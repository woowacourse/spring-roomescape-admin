package roomescape.reservation;

import roomescape.reservation.domain.Reservation;
import roomescape.reservationTime.domain.ReservationTime;

import java.time.LocalDate;

public class ReservationHelper {

    public Reservation createReservation(String name, int plusDays, ReservationTime time) {
        LocalDate date = LocalDate.now().plusDays(plusDays);
        return Reservation.of(name, date, time);
    }
}

