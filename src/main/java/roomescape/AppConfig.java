package roomescape;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.reservation.JdbcReservationRepository;
import roomescape.reservationTime.JdbcReservationTimeRepository;
import roomescape.reservation.ReservationRepository;
import roomescape.reservation.ReservationService;
import roomescape.reservation.ReservationServiceImpl;
import roomescape.reservationTime.ReservationTimeRepository;
import roomescape.reservationTime.ReservationTimeService;
import roomescape.reservationTime.ReservationTimeServiceImpl;

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

    @Bean
    public ReservationTimeRepository reservationTimeRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcReservationTimeRepository(jdbcTemplate);
    }

    @Bean
    public ReservationTimeService reservationTimeService(JdbcTemplate jdbcTemplate) {
        return new ReservationTimeServiceImpl(reservationTimeRepository(jdbcTemplate));
    }

}
