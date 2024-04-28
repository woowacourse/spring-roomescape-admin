package roomescape.fixture;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.entity.ReservationTime;

public class DateTimeFixture {
    public static final LocalDate DAY_AFTER_TOMORROW = LocalDate.now().plusDays(2);
    public static final ReservationTime GAME_TIME_WITH_ID_0300 = new ReservationTime(1L, LocalTime.of(3, 0));
    public static final ReservationTime GAME_TIME_WITH_NO_ID_0300 = new ReservationTime(LocalTime.of(3, 0));
    public static final ReservationTime GAME_TIME_WITH_NO_ID_0400 = new ReservationTime(LocalTime.of(4, 0));
}
