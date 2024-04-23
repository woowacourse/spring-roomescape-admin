package roomescape.fixture;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.ReservationTime;

public class ReservationFixture {
    private static final LocalDate FIXED_DATE = LocalDate.of(2024, 4, 18);
    private static final LocalTime FIXED_TIME = LocalTime.of(12, 0);

    public static Reservation reservation(String name) {
        return reservation(name, FIXED_DATE, FIXED_TIME);
    }

    public static Reservation reservation(String name, LocalDate date, LocalTime time) {
        return new Reservation(name, date, reservationTime(time));
    }

    public static ReservationTime reservationTime(LocalTime startAt) {
        return new ReservationTime(1L, startAt);
    }
}
