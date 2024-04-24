package roomescape.fixture;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.entity.GameTime;

public class DateTimeFixture {
    public static final LocalDateTime DATE_2024_04_20_TIME_03_00 = LocalDateTime.of(2024, 4, 20, 3, 0);
    public static final LocalDate DATE_2024_04_20 = LocalDate.of(2024, 4, 20);
    public static final GameTime TIME_03_00_WITH_ID = new GameTime(1L, LocalTime.of(3, 0));
    public static final GameTime TIME_03_00_NO_ID = new GameTime(LocalTime.of(3, 0));
    public static final GameTime TIME_04_00_NO_ID = new GameTime(LocalTime.of(4, 0));
}
