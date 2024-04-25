package roomescape.console.config;

import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.console.controller.ControllerMapper;
import roomescape.console.controller.ReservationConsoleController;
import roomescape.console.controller.ReservationTimeConsoleController;

@Configuration
public class ControllerMapperConfig {

    private final ReservationConsoleController reservationConsoleController;
    private final ReservationTimeConsoleController reservationTimeConsoleController;

    public ControllerMapperConfig(ReservationConsoleController reservationConsoleController,
                                  ReservationTimeConsoleController reservationTimeConsoleController) {
        this.reservationConsoleController = reservationConsoleController;
        this.reservationTimeConsoleController = reservationTimeConsoleController;
    }

    @Bean
    public ControllerMapper controllerMapper() {
        return new ControllerMapper(Map.of(
                1, reservationTimeConsoleController,
                2, reservationConsoleController
        ));
    }
}
