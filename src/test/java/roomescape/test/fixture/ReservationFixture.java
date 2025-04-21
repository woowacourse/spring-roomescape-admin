package roomescape.test.fixture;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class ReservationFixture {

    public static Reservation createReservation(String name, ReservationTime reservationTime) {
        return Reservation.createWithoutId(
                name, LocalDate.now().plusDays(1), reservationTime);
    }

    public static Reservation createReservation(String name, LocalDate date, ReservationTime reservationTime) {
        return Reservation.createWithoutId(name, date, reservationTime);
    }
}
