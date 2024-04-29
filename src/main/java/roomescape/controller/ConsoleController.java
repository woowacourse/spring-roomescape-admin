package roomescape.controller;

import roomescape.controller.command.Command;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.ConsoleView;
import roomescape.view.InputCommandMapper;

public class ConsoleController {

    private final ConsoleView consoleView;
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ConsoleController(ConsoleView consoleView, ReservationService reservationService, ReservationTimeService reservationTimeService) {
        this.consoleView = consoleView;
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    public void run() {
        while (true) {
            String rawCommand = consoleView.readCommand();
            Command command = InputCommandMapper.findCommand(rawCommand, consoleView, reservationService, reservationTimeService);
            command.execute();
        }
    }
}
