package roomescape.config;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestClockConfig {

    @Bean
    public Clock clock() {
        LocalDateTime fixedDateTime = LocalDateTime.of(2025, 1, 1, 12, 0);
        return Clock.fixed(fixedDateTime.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
    }
}
