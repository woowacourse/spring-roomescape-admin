package roomescape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.repository.JdbcReservationTimeRepository;
import roomescape.repository.ReservationTimeRepository;

@Configuration
public class ReservationTimeConfig {

    @Bean
    public ReservationTimeRepository reservationTimeRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcReservationTimeRepository(jdbcTemplate);
    }
}
