package roomescape.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import roomescape.console.controller.MainController;
import roomescape.console.repository.ReservationMemoryRepository;
import roomescape.console.repository.ReservationTimeMemoryRepository;
import roomescape.core.service.ReservationService;
import roomescape.core.service.ReservationTimeService;

@SpringBootApplication(scanBasePackages = {"roomescape.console", "roomescape.core"})
public class RoomescapeConsoleApplicaition {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RoomescapeConsoleApplicaition.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);

        MainController mainController = new MainController(
                new ReservationService(new ReservationMemoryRepository()),
                new ReservationTimeService(new ReservationTimeMemoryRepository()));
        mainController.run();
    }
}


