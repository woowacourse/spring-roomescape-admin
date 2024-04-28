package roomescape;

import roomescape.console.controller.ConsoleController;
import roomescape.console.domain.ConsoleReservationRepository;
import roomescape.console.domain.ConsoleReservationTimeRepository;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.domain.ReservationTimeRepository;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

public class ConsoleApplication {
    public static void main(String[] args) {
        ReservationTimeRepository reservationTimeRepository = new ConsoleReservationTimeRepository();
        ConsoleController consoleController = new ConsoleController(
                new OutputView(),
                new InputView(),
                new ReservationTimeService(reservationTimeRepository),
                new ReservationService(new ConsoleReservationRepository(), reservationTimeRepository)
        );
        consoleController.run();
    }
}
