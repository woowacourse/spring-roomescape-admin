package roomescape.config;

import java.time.LocalDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultTimeConfig implements TimeConfig {

    @Bean
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
