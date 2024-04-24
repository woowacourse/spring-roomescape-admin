package roomescape.fixture;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.entity.GameTime;

public class DateTimeFixture {
    public static final LocalDate DAY_AFTER_TOMORROW = LocalDate.now().plusDays(2);
    public static final GameTime GAME_TIME_WITH_ID_0300 = new GameTime(1L, LocalTime.of(3, 0));
    public static final GameTime GAME_TIME_WITH_NO_ID_0300 = new GameTime(LocalTime.of(3, 0));
    public static final GameTime GAME_TIME_WITH_NO_ID_0400 = new GameTime(LocalTime.of(4, 0));
}
