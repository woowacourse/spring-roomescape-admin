package roomescape.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import roomescape.console.config.ConsoleConfig;
import roomescape.console.controller.MainController;
import roomescape.core.repository.ReservationJdbcRepository;
import roomescape.core.repository.ReservationMemoryRepository;
import roomescape.core.repository.ReservationTimeJdbcRepository;
import roomescape.core.repository.ReservationTimeMemoryRepository;
import roomescape.core.service.ReservationService;
import roomescape.core.service.ReservationTimeService;


public class RoomescapeConsoleApplicaition {
    public static void main(String[] args) {
        final MainController frontController = ConsoleConfig.mainController();
        frontController.run();
    }
}


