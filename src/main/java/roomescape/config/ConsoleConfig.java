package roomescape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import roomescape.console.ConsoleRunner;
import roomescape.console.controller.ConsoleInputConverter;
import roomescape.console.controller.ConsoleReservationController;
import roomescape.console.controller.ConsoleReservationTimeController;
import roomescape.console.controller.DispatcherConsole;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

@Configuration
@Profile("console")
public class ConsoleConfig {

    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ConsoleConfig(ReservationService reservationService, ReservationTimeService reservationTimeService) {
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    @Bean
    public ConsoleRunner consoleRunner() {
        return new ConsoleRunner(dispatcherConsole(), inputView(), outputView());
    }

    @Bean
    public DispatcherConsole dispatcherConsole() {
        return new DispatcherConsole(consoleReservationController(), consoleReservationTimeController(),
                consoleInputConverter());
    }

    @Bean
    public ConsoleReservationController consoleReservationController() {
        return new ConsoleReservationController(reservationService, outputView());
    }

    @Bean
    public ConsoleReservationTimeController consoleReservationTimeController() {
        return new ConsoleReservationTimeController(reservationTimeService, outputView());
    }

    @Bean
    public ConsoleInputConverter consoleInputConverter() {
        return new ConsoleInputConverter();
    }

    @Bean
    public InputView inputView() {
        return new InputView();
    }

    @Bean
    public OutputView outputView() {
        return new OutputView();
    }
}
