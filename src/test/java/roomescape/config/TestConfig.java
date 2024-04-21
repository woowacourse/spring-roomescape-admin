package roomescape.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.domain.Reservation;
import roomescape.storage.ReservationStorage;

@Configuration
public class TestConfig {
    @Bean
    ReservationStorage reservationStorage() {
        return new ReservationStorage(reservations(), atomicLong());
    }

    @Bean
    List<Reservation> reservations() {
        return new ArrayList<>();
    }

    @Bean
    AtomicLong atomicLong() {
        return new AtomicLong(0);
    }
}
