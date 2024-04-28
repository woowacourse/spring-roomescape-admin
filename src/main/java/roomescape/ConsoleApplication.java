package roomescape;

import roomescape.controller.ReservationConsoleController;
import roomescape.repository.ReservationMemoryRepository;
import roomescape.repository.ReservationTimeMemoryRepository;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public class ConsoleApplication {
    public static void main(String[] args) {

        ReservationMemoryRepository reservationRepository = new ReservationMemoryRepository();
        ReservationTimeMemoryRepository reservationTimeRepository = new ReservationTimeMemoryRepository();

        ReservationConsoleController reservationController = new ReservationConsoleController(
                new InputView(),
                new OutputView(),
                new ReservationService(reservationRepository, reservationTimeRepository),
                new ReservationTimeService(reservationTimeRepository));

        reservationController.start();
    }
}
