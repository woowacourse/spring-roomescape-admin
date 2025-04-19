package roomescape.common;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Constant {

    public static Clock FIXED_CLOCK = Clock.fixed(LocalDateTime.of(2025, 1, 1, 12, 0)
            .atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
}
