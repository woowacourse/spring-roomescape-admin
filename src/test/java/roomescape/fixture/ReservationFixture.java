package roomescape.fixture;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public abstract class ReservationFixture {

    public static final String NAME = "test name";
    public static final LocalDate BASE_DATE = LocalDate.now().plusDays(1);
    public static final LocalTime BASE_TIME = LocalTime.now();

    public static Reservation entity(ReservationTime time) {
        return new Reservation(NAME, BASE_DATE, time);
    }
}
