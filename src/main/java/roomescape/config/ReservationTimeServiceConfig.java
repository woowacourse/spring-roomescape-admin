package roomescape.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.ReservationTimeService;

@Configuration
public class ReservationTimeServiceConfig {
    @Bean
    public ReservationTimeService webReservationTimeService(
            @Qualifier("reservationTimeJdbcRepository") ReservationTimeRepository repository) {
        return new ReservationTimeService(repository);
    }

    @Bean
    public ReservationTimeService consoleReservationTimeService(
            @Qualifier("reservationTimeMemoryRepository") ReservationTimeRepository repository) {
        return new ReservationTimeService(repository);
    }
}
