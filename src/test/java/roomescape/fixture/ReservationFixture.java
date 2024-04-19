package roomescape.fixture;

import static roomescape.fixture.ClockFixture.fixedClock;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDateTime;

public class ReservationFixture {
    private static final int IN_ADVANCE_RESERVATION_DAYS = 1;
    private static final LocalDate FIXED_DATE = LocalDate.of(2024, 4, 18);
    private static final LocalTime FIXED_TIME = LocalTime.of(12, 0);

    public static Reservation reservation(String name) {
        return new Reservation(name, reservationDateTime(FIXED_DATE, FIXED_TIME));
    }

    public static Reservation reservation(String name, LocalDate date, LocalTime time) {
        return new Reservation(name, reservationDateTime(date, time));
    }

    public static ReservationDateTime reservationDateTime(LocalDate date, LocalTime time) {
        return new ReservationDateTime(date, time, fixedClock(date.minusDays(IN_ADVANCE_RESERVATION_DAYS)));
    }
}
