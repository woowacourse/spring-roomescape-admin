package roomescape.console;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.console.controller.ConsoleController;
import roomescape.console.controller.ReservationTimeController;
import roomescape.console.utils.Command;
import roomescape.console.utils.ControllerMapper;

import java.util.Map;

@Configuration
public class AppConfig {
    @Bean
    public ControllerMapper controllerMapper(ReservationTimeController reservationTimeController) {
        return new ControllerMapper(controllers(reservationTimeController));
    }

    @Bean
    public Map<Command, ConsoleController> controllers(ReservationTimeController reservationTimeController) {
        return Map.of(Command.TIMES, reservationTimeController);
    }
}
