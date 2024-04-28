package roomescape;

import roomescape.console.controller.ConsoleController;
import roomescape.console.domain.ConsoleReservationTimeRepository;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.service.ReservationTimeService;

public class ConsoleApplication {
    public static void main(String[] args) {
        ConsoleController consoleController = new ConsoleController(
                new OutputView(),
                new InputView(),
                new ReservationTimeService(new ConsoleReservationTimeRepository())
        );
        consoleController.run();
    }
}
