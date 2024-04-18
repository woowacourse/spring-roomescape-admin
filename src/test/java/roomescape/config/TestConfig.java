package roomescape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.domain.Reservation;
import roomescape.storage.ReservationStorage;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class TestConfig {
    @Bean
    ReservationStorage reservationStorage() {
        return new ReservationStorage(reservations(), atomicLong());
    }

    @Bean
    List<Reservation> reservations() {
        return new CopyOnWriteArrayList<>();
    }

    @Bean
    AtomicLong atomicLong() {
        return new AtomicLong(0);
    }
}
