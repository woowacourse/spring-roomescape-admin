package roomescape.controller;

import roomescape.controller.command.*;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.ConsoleView;

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
            Class<? extends Command> commandType = consoleView.readCommand();
            Command command = createCommand(commandType);
            command.execute();
        }
    }

    private Command createCommand(Class<? extends Command> commandType) {
        if (commandType == TimeShowCommand.class) {
            return new TimeShowCommand(consoleView, reservationTimeService);
        }
        if (commandType == TimeSaveCommand.class) {
            return new TimeSaveCommand(consoleView, reservationTimeService);
        }
        if (commandType == TimeDeleteCommand.class) {
            return new TimeDeleteCommand(consoleView, reservationTimeService);
        }
        if (commandType == ReservationShowCommand.class) {
            return new ReservationShowCommand(consoleView, reservationService);
        }
        if (commandType == ReservationSaveCommand.class) {
            return new ReservationSaveCommand(consoleView, reservationService);
        }
        if (commandType == ReservationDeleteCommand.class) {
            return new ReservationDeleteCommand(consoleView, reservationService);
        }
        return new ExitCommand(consoleView);
    }
}
