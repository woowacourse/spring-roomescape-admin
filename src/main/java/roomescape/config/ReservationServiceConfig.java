package roomescape.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.persistence.repository.reservation.ReservationRepository;
import roomescape.persistence.repository.reservationtime.ReservationTimeRepository;
import roomescape.service.ReservationService;

@Configuration
public class ReservationServiceConfig {
    
    @Bean
    public ReservationService webReservationService(
            @Qualifier("reservationJdbcRepository") ReservationRepository reservationRepository,
            @Qualifier("reservationTimeJdbcRepository") ReservationTimeRepository reservationTimeRepository) {
        return new ReservationService(reservationRepository, reservationTimeRepository);
    }

    @Bean
    public ReservationService consoleReservationService(
            @Qualifier("reservationMemoryRepository") ReservationRepository reservationRepository,
            @Qualifier("reservationTimeMemoryRepository") ReservationTimeRepository reservationTimeRepository) {
        return new ReservationService(reservationRepository, reservationTimeRepository);
    }
}
