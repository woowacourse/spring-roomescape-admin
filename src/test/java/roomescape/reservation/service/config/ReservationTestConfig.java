package roomescape.reservation.service.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import roomescape.reservation.service.ReservationRepository;
import roomescape.reservation.service.ReservationService;
import roomescape.time.service.ReservationTimeRepository;
import roomescape.time.service.ReservationTimeService;
import roomescape.time.service.ReservationTimeServiceImpl;

@TestConfiguration
public class ReservationTestConfig {

    @Bean
    public ReservationService reservationService() {
        return new ReservationService(stubReservationRepository(), stubReservationTimeService());
    }

    @Bean
    public ReservationRepository stubReservationRepository() {
        return new StubReservationRepository();
    }

    @Bean
    public ReservationTimeService stubReservationTimeService() {
        return new StubReservationTimeService();
    }

    @Bean
    public ReservationTimeService reservationTimeService() {
        return new ReservationTimeServiceImpl(stubReservationTimeRepository());
    }

    @Bean
    public ReservationTimeRepository stubReservationTimeRepository() {
        return new StubReservationTimeRepository();
    }

}
