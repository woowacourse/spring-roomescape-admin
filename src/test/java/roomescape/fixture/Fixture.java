package roomescape.fixture;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class Fixture {

    public final static ReservationTime RESERVATION_TIME_1 = reservationTime(10, 30);
    public final static ReservationTime RESERVATION_TIME_2 = reservationTime(11, 30);

    public static ReservationTime reservationTime(int hour, int minute) {
        return new ReservationTime(LocalTime.of(hour, minute));
    }

    public static Reservation reservation(String name, int year, int month, int day, ReservationTime time) {
        LocalDate date = LocalDate.of(year, month, day);

        return new Reservation(name, date, time);
    }
}
