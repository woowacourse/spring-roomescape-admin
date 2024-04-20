package roomescape.fixture;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class ClockFixture {
    public static Clock fixedClock(LocalDate localDate) {
        return Clock.fixed(Instant.parse(format(localDate)), ZoneId.of("UTC"));
    }

    private static String format(LocalDate localDate) {
        return localDate.toString() + "T00:00:00Z";
    }
}
