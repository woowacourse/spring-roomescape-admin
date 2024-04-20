package roomescape.config;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.storage.ReservationStorage;

@Configuration
public class TestConfig {
    @Bean
    ReservationStorage reservationStorage() {
        return new ReservationStorage(new CopyOnWriteArrayList<>(), new AtomicLong(0));
    }
}
