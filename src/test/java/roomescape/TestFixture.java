package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class TestFixture {
    private TestFixture() {
    }

    public static ReservationTime createTime() {
        return new ReservationTime(1L, LocalTime.of(10, 0));
    }


    public static Reservation createReservation() {
        return new Reservation(1L, "티온", LocalDate.of(2026, 5, 3), createTime());
    }
}
