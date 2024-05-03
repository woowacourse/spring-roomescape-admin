package roomescape.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import roomescape.core.repository.ReservationJdbcRepository;
import roomescape.core.repository.ReservationMemoryRepository;
import roomescape.core.repository.ReservationTimeJdbcRepository;
import roomescape.core.repository.ReservationTimeMemoryRepository;

@SpringBootApplication(scanBasePackages = {"roomescape.web", "roomescape.core"})
public class RoomescapeApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoomescapeApplication.class, args);
    }
}
