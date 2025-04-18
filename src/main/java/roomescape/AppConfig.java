package roomescape;

import org.springframework.context.annotation.Bean;
import roomescape.repository.ReservationRepository;
import roomescape.service.ReservationService;

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
