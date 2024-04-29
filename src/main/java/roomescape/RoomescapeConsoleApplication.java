package roomescape;

import roomescape.console.dao.ReservationMemoryDao;
import roomescape.console.dao.ReservationTimeMemoryDao;
import roomescape.console.presentation.DispatcherConsole;
import roomescape.console.presentation.ReservationConsoleController;
import roomescape.console.presentation.ReservationTimeConsoleController;
import roomescape.console.view.Command;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.core.domain.ReservationRepository;
import roomescape.core.domain.ReservationTimeRepository;
import roomescape.core.service.ReservationService;
import roomescape.core.service.ReservationTimeService;

public class RoomescapeConsoleApplication {
    public static void main(String[] args) {
        ReservationRepository reservationRepository = new ReservationMemoryDao();
        ReservationTimeRepository reservationTimeRepository = new ReservationTimeMemoryDao();

        ReservationService reservationService = new ReservationService(
                reservationRepository, reservationTimeRepository);
        ReservationTimeService reservationTimeService = new ReservationTimeService(
                reservationTimeRepository);
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        ReservationConsoleController reservationController = new ReservationConsoleController(
                reservationService, outputView, inputView);
        ReservationTimeConsoleController reservationTimeController = new ReservationTimeConsoleController(
                reservationTimeService, outputView, inputView);

        Command command;
        DispatcherConsole dispatcherConsole = new DispatcherConsole(reservationController, reservationTimeController);
        do {
            outputView.printCommand();
            command = inputView.askCommand();
            dispatcherConsole.doDispatch(command);
        } while (command != Command.END);
    }
}
