package roomescape;

import roomescape.consolerunner.RoomEscapeConsoleRunner;
import roomescape.controller.console.ReservationConsoleController;
import roomescape.controller.console.ReservationTimeConsoleController;
import roomescape.dao.ReservationRepository;
import roomescape.dao.ReservationTimeRepository;
import roomescape.dao.console.ReservationConsoleRepository;
import roomescape.dao.console.ReservationTimeConsoleRepository;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.console.ConsoleInputView;
import roomescape.view.console.ConsoleOutputView;

public class RoomEscapeConsoleApplication {

    public static void main(String[] args) {
        ReservationRepository reservationRepository = new ReservationConsoleRepository();
        ReservationTimeRepository reservationTimeRepository = new ReservationTimeConsoleRepository();
        ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeRepository);
        ReservationService reservationService = new ReservationService(reservationRepository);


        RoomEscapeConsoleRunner consoleRunner = new RoomEscapeConsoleRunner(
                new ReservationConsoleController(reservationService, reservationTimeService),
                new ReservationTimeConsoleController(reservationTimeService),
                new ConsoleInputView(),
                new ConsoleOutputView()
        );
        consoleRunner.run();
    }
}
