package roomescape.config;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestClockConfig {

    @Bean
    @Primary
    public Clock testClock() {
        LocalDateTime fixedDateTime = LocalDateTime.of(2025, 1, 1, 0, 0);
        return Clock.fixed(fixedDateTime.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
    }
}
