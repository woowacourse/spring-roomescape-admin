package roomescape.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.repository.reservation.ReservationRepository;
import roomescape.service.ReservationService;

@Configuration
public class ReservationServiceConfig {
    @Bean
    public ReservationService webReservationService(
            @Qualifier("reservationJdbcRepository") ReservationRepository repository) {
        return new ReservationService(repository);
    }

    @Bean
    public ReservationService consoleReservationService(
            @Qualifier("reservationMemoryRepository") ReservationRepository repository) {
        return new ReservationService(repository);
    }
}
