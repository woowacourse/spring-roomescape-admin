package roomescape.console;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.console.controller.ControllerAdapter;
import roomescape.console.controller.ReservationControllerAdapter;
import roomescape.console.controller.ReservationTimeControllerAdapter;
import roomescape.console.utils.ConsoleCommand;
import roomescape.console.utils.ControllerAdapterMapper;

import java.util.Map;

@Configuration
public class AppConfig {
    @Bean
    public ControllerAdapterMapper controllerAdapterMapper(
            ReservationTimeControllerAdapter reservationTimeControllerAdapter,
            ReservationControllerAdapter reservationControllerAdapter
    ) {
        return new ControllerAdapterMapper(controllers(
                reservationTimeControllerAdapter,
                reservationControllerAdapter));
    }

    @Bean
    public Map<ConsoleCommand, ControllerAdapter> controllers(
            ReservationTimeControllerAdapter reservationTimeControllerAdapter,
            ReservationControllerAdapter reservationControllerAdapter
    ) {
        return Map.of(ConsoleCommand.TIMES, reservationTimeControllerAdapter,
                ConsoleCommand.RESERVATIONS, reservationControllerAdapter);
    }
}
