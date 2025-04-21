package roomescape;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.reservation.JdbcReservationRepository;
import roomescape.reservation.ReservationRepository;
import roomescape.reservation.Reservations;

@Configuration
public class AppConfig {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean
    public Reservations reservations() {
        return new Reservations(List.of());
    }

    @Bean
    public ReservationRepository reservationRepository() {
        return new JdbcReservationRepository(jdbcTemplate);
    }

}
