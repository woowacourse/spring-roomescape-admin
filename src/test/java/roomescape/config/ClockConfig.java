package roomescape.config;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ClockConfig {

    @Bean
    public Clock clock() {
        LocalDateTime fixedDateTime = LocalDateTime.of(2025, 1, 1, 0, 0);
        return Clock.fixed(fixedDateTime.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
    }
}
