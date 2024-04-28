package roomescape.console.config;

import java.util.Map;
import roomescape.console.controller.MainConsoleController;
import roomescape.console.controller.ReservationConsoleController;
import roomescape.console.controller.ReservationTimeConsoleController;
import roomescape.console.view.AdminMenu;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;

public class ControllerFactory {

    private static final ControllerFactory INSTANCE = new ControllerFactory();
    private static final ReservationConsoleController RESERVATION_CONSOLE_CONTROLLER;
    private static final ReservationTimeConsoleController RESERVATION_TIME_CONSOLE_CONTROLLER;
    private static final MainConsoleController MAIN_CONSOLE_CONTROLLER;

    static {
        RESERVATION_CONSOLE_CONTROLLER = new ReservationConsoleController(
                InputView.getInstance(), OutputView.getInstance(),
                ServiceFactory.getInstance().getReservationService()
        );

        RESERVATION_TIME_CONSOLE_CONTROLLER = new ReservationTimeConsoleController(
                InputView.getInstance(), OutputView.getInstance(),
                ServiceFactory.getInstance().getReservationTimeService()
        );

        MAIN_CONSOLE_CONTROLLER = new MainConsoleController(
                InputView.getInstance(),OutputView.getInstance(), createMapper()
        );
    }

    private ControllerFactory() {
    }

    private static ControllerMapper createMapper() {
        return new ControllerMapper(Map.of(
                AdminMenu.RESERVATION_TIME, RESERVATION_TIME_CONSOLE_CONTROLLER,
                AdminMenu.RESERVATION, RESERVATION_CONSOLE_CONTROLLER
        ));
    }

    public static ControllerFactory getInstance() {
        return INSTANCE;
    }

    public MainConsoleController getMainConsoleController() {
        return MAIN_CONSOLE_CONTROLLER;
    }
}
