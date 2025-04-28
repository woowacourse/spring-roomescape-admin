package roomescape.infra.memory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import roomescape.infra.ReservationTimeDatabase;

@TestConfiguration
public class MemoryDatabaseTestConfig {

    @Bean
    @Qualifier("reservationTimeMemoryDatabase")
    public ReservationTimeDatabase reservationTimeMemoryDatabase(IdGenerator idGenerator) {
        return new ReservationTimeMemoryDatabase(idGenerator);
    }

    @Bean
    public ReservationMemoryDatabase reservationMemoryDatabase(
            ReservationTimeDatabase timeDatabase,
            IdGenerator idGenerator
    ) {
        return new ReservationMemoryDatabase(timeDatabase, idGenerator);
    }

    @Bean
    public IdGenerator idGenerator() {
        return new IdGenerator();
    }
}
