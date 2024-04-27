package roomescape.console;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.console.controller.ConsoleController;
import roomescape.console.controller.ReservationTimeController;
import roomescape.console.utils.Command;

import java.util.Map;

@Configuration
public class AppConfig {
    @Bean
    public Map<Command, ConsoleController> controllers(ReservationTimeController reservationTimeController) {
        return Map.of(Command.TIMES, reservationTimeController);
    }
}
