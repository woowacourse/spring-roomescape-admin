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
    private static final ReservationConsoleController reservationConsoleController;
    private static final ReservationTimeConsoleController reservationTimeConsoleController;
    private static final MainConsoleController mainConsoleController;

    static {
        reservationConsoleController = new ReservationConsoleController(
                InputView.getInstance(), OutputView.getInstance(),
                ServiceFactory.getInstance().getReservationService()
        );

        reservationTimeConsoleController = new ReservationTimeConsoleController(
                InputView.getInstance(), OutputView.getInstance(),
                ServiceFactory.getInstance().getReservationTimeService()
        );

        mainConsoleController = new MainConsoleController(
                InputView.getInstance(),OutputView.getInstance(), createMapper()
        );
    }

    private static ControllerMapper createMapper() {
        return new ControllerMapper(Map.of(
                AdminMenu.RESERVATION_TIME, reservationTimeConsoleController,
                AdminMenu.RESERVATION, reservationConsoleController
        ));
    }

    public static ControllerFactory getInstance() {
        return INSTANCE;
    }

    public MainConsoleController getMainConsoleController() {
        return mainConsoleController;
    }
}
