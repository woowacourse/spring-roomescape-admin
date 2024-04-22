package roomescape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import roomescape.repository.H2ReservationRepository;
import roomescape.repository.ReservationRepository;

@Configuration
public class AppConfig {

    private final NamedParameterJdbcTemplate template;

    public AppConfig(final NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Bean
    public ReservationRepository reservationRepository() {
        return new H2ReservationRepository(template);
    }
}
