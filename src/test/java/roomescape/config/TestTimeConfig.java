package roomescape.config;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestTimeConfig {

    @Bean
    @Primary
    public Clock testNow() {
        return Clock.fixed(LocalDateTime.of(2030, 5, 18, 10, 0).atZone(ZoneId.systemDefault()).toInstant(),
                ZoneId.systemDefault());
    }
}
