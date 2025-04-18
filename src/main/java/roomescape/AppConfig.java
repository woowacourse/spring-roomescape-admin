package roomescape;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.repository.ReservationRepository;
import roomescape.service.ReservationService;

@Configuration
public class AppConfig {

    @Bean
    public ReservationService reservationService() {
        return new ReservationService(reservationRepository());
    }

    @Bean
    public ReservationRepository reservationRepository() {
        return new ReservationRepository();
    }
}
