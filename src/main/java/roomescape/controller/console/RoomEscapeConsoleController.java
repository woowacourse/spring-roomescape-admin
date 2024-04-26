package roomescape.controller.console;

import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public class RoomEscapeConsoleController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public RoomEscapeConsoleController(InputView inputView,
                                       OutputView outputView,
                                       ReservationService reservationService,
                                       ReservationTimeService reservationTimeService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    public void run() {

    }
}
