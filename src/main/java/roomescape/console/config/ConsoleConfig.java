package roomescape.console.config;

import roomescape.console.controller.ConsoleRunner;
import roomescape.console.controller.ConsoleInputConverter;
import roomescape.console.controller.ConsoleReservationController;
import roomescape.console.controller.ConsoleReservationTimeController;
import roomescape.console.controller.DispatcherConsole;
import roomescape.console.repository.MemoryReservationRepository;
import roomescape.console.repository.MemoryReservationTimeRepository;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.core.repository.ReservationRepository;
import roomescape.core.service.ReservationService;
import roomescape.core.repository.ReservationTimeRepository;
import roomescape.core.service.ReservationTimeService;

public class ConsoleConfig {

    private ConsoleConfig() {
    }

    public static ConsoleRunner consoleRunner() {
        return new ConsoleRunner(dispatcherConsole(), inputView(), outputView());
    }

    private static DispatcherConsole dispatcherConsole() {
        return new DispatcherConsole(consoleReservationController(), consoleReservationTimeController(),
                consoleInputConverter());
    }

    private static ConsoleReservationController consoleReservationController() {
        return new ConsoleReservationController(reservationService(), outputView());
    }

    private static ConsoleReservationTimeController consoleReservationTimeController() {
        return new ConsoleReservationTimeController(reservationTimeService(), outputView());
    }

    private static ReservationService reservationService() {
        return new ReservationService(reservationRepository(), reservationTimeRepository());
    }

    private static ReservationTimeService reservationTimeService() {
        return new ReservationTimeService(reservationTimeRepository(), reservationRepository());
    }

    private static ReservationRepository reservationRepository() {
        return new MemoryReservationRepository();
    }

    private static ReservationTimeRepository reservationTimeRepository() {
        return new MemoryReservationTimeRepository();
    }

    private static ConsoleInputConverter consoleInputConverter() {
        return new ConsoleInputConverter();
    }

    private static InputView inputView() {
        return new InputView();
    }

    private static OutputView outputView() {
        return new OutputView();
    }
}
