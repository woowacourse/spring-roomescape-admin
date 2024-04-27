package roomescape.controller.console;

import roomescape.controller.console.command.Command;
import roomescape.controller.console.command.commandmapper.RoomEscapeCommandMapper;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public class ConsoleController {
    private final InputView inputView;
    private final OutputView outputView;
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ConsoleController(InputView inputView,
                             OutputView outputView,
                             ReservationService reservationService,
                             ReservationTimeService reservationTimeService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    public void run() {
        Command command;
        do {
            command = RoomEscapeCommandMapper.mapTo(inputView, outputView);
            command.execute(reservationService, reservationTimeService, inputView, outputView);
        } while (!command.isEnd());
    }
}
