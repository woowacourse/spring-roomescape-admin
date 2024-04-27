package roomescape.console;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.console.controller.ControllerAdapter;
import roomescape.console.controller.ReservationTimeControllerAdapter;
import roomescape.console.utils.Command;
import roomescape.console.utils.ControllerAdapterMapper;

import java.util.Map;

@Configuration
public class AppConfig {
    @Bean
    public ControllerAdapterMapper controllerMapper(ReservationTimeControllerAdapter reservationTimeControllerAdapter) {
        return new ControllerAdapterMapper(controllers(reservationTimeControllerAdapter));
    }

    @Bean
    public Map<Command, ControllerAdapter> controllers(ReservationTimeControllerAdapter reservationTimeControllerAdapter) {
        return Map.of(Command.TIMES, reservationTimeControllerAdapter);
    }
}
