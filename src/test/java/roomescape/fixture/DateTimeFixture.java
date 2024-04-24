package roomescape.fixture;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.entity.ReservationTime;

public class DateTimeFixture {
    public static final LocalDateTime DATE_2024_04_20_TIME_03_00 = LocalDateTime.of(2024, 4, 20, 3, 0);
    public static final LocalDate DATE_2024_04_20 = LocalDate.of(2024, 4, 20);
    public static final ReservationTime TIME_03_00 = new ReservationTime(1L, LocalTime.of(3, 0));
}
