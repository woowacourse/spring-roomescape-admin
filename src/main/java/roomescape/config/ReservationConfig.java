package roomescape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.repository.JdbcReservationRepository;
import roomescape.repository.ReservationRepository;

@Configuration
public class ReservationConfig {

    @Bean
    public ReservationRepository reservationRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcReservationRepository(jdbcTemplate);
    }

}
