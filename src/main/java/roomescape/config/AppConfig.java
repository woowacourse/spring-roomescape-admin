package roomescape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationRepository;
import roomescape.repository.JdbcReservationRepository;

@Configuration
public class AppConfig {

    private final JdbcTemplate jdbcTemplate;

    public AppConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    public ReservationRepository reservationDao() {
        return new JdbcReservationRepository(jdbcTemplate);
    }
}
