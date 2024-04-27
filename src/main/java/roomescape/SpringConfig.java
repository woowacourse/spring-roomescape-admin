package roomescape;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.web.dao.ReservationDao;
import roomescape.repository.web.dao.ReservationTimeDao;
import roomescape.repository.web.dao.rowmapper.ReservationRowMapper;
import roomescape.repository.web.dao.rowmapper.ReservationTimeRowMapper;

@Configuration
public class SpringConfig {

    @Bean
    ReservationTimeRepository reservationTimeRepository(JdbcTemplate jdbcTemplate) {
        return new ReservationTimeDao(jdbcTemplate, new ReservationTimeRowMapper());
    }

    @Bean
    ReservationRepository reservationRepository(JdbcTemplate jdbcTemplate) {
        return new ReservationDao(jdbcTemplate, new ReservationRowMapper());
    }
}
