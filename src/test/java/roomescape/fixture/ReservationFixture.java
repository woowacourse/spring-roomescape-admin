package roomescape.fixture;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.ReservationTime;

public class ReservationFixture {
    public static Reservation reservation() {
        return new Reservation(1L, "prin", LocalDate.of(2024, 4, 23), reservationTime());
    }

    public static ReservationTime reservationTime() {
        return new ReservationTime(1L, LocalTime.of(22, 11));
    }
}
