package roomescape.fixture;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.ReservationTime;

public class ReservationFixture {
    public static Reservation reservation() {
        return reservation(1L);
    }

    public static Reservation reservation(long id) {
        return new Reservation(id, "prin", LocalDate.of(2024, 4, 23), reservationTime());
    }

    public static ReservationTime reservationTime() {
        return reservationTime(1L);
    }

    public static ReservationTime reservationTime(long id) {
        return new ReservationTime(id, LocalTime.of(22, 11));
    }
}
