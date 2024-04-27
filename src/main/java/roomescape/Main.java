package roomescape;

import roomescape.controller.console.RoomEscapeConsoleController;
import roomescape.respository.ReservationRepository;
import roomescape.respository.ReservationRepositoryImpl;
import roomescape.respository.ReservationTimeRepository;
import roomescape.respository.ReservationTimeRepositoryImpl;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public class Main {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        ReservationRepository reservationRepository = new ReservationRepositoryImpl();
        ReservationTimeRepository reservationTimeRepository = new ReservationTimeRepositoryImpl();

        ReservationService reservationService = new ReservationService(
                reservationRepository,
                reservationTimeRepository
        );

        ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeRepository);

        RoomEscapeConsoleController roomEscapeConsoleController = new RoomEscapeConsoleController(
                inputView,
                outputView,
                reservationService,
                reservationTimeService
        );

        roomEscapeConsoleController.run();
    }
}
