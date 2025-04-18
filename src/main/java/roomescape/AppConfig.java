package roomescape;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.entity.Reservations;

@Configuration
public class AppConfig {

    @Bean
    public Reservations reservations() {
        return new Reservations(List.of());
    }

}
