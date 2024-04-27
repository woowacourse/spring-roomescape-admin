package roomescape;

import roomescape.controller.console.ConsoleController;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.console.MemoryReservationRepository;
import roomescape.repository.console.MemoryReservationTimeRepository;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public class RoomEscapeConsoleApplication {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        ReservationRepository reservationRepository = new MemoryReservationRepository();
        ReservationTimeRepository reservationTimeRepository = new MemoryReservationTimeRepository();
        ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeRepository);
        ReservationService reservationService = new ReservationService(reservationRepository, reservationTimeRepository);
        ConsoleController consoleController = new ConsoleController(inputView, outputView, reservationService, reservationTimeService);

        consoleController.run();
    }
}
