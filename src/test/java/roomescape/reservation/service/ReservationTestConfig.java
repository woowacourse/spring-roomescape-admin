package roomescape.reservation.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import roomescape.time.service.ReservationTimeService;

@TestConfiguration
public class ReservationTestConfig {

    @Bean
    public ReservationService reservationService() {
        return new ReservationService(reservationRepository(), reservationTimeService());
    }

    @Bean
    public ReservationRepository reservationRepository() {
        return new StubReservationRepository();
    }

    @Bean
    public ReservationTimeService reservationTimeService() {
        return new StubReservationTimeService();
    }

}
