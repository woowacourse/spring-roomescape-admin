package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.reservation.JdbcReservationRepository;
import roomescape.reservation.ReservationRepository;
import roomescape.reservation.ReservationService;
import roomescape.reservation.ReservationServiceImpl;

@Configuration
public class AppConfig {

    @Bean
    public ReservationRepository reservationRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcReservationRepository(jdbcTemplate);
    }

    @Bean
    public ReservationService reservationService(ReservationRepository reservationRepository) {
        return new ReservationServiceImpl(reservationRepository);
    }

}
