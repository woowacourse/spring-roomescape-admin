package roomescape.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import roomescape.domain.ReservationRepository;
import roomescape.fake.FakeReservationRepository;

@TestConfiguration
public class TestConfig {

    @Bean
    public ReservationRepository reservationRepository() {
        return new FakeReservationRepository();
    }
}
