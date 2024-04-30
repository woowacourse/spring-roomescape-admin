package roomescape.controller.command;

import roomescape.service.ReservationTimeService;
import roomescape.view.ConsoleView;

public class TimeShowCommand extends TimeCommand {

    public TimeShowCommand(ConsoleView consoleView, ReservationTimeService reservationTimeService) {
        super(consoleView, reservationTimeService);
    }

    @Override
    public void execute() {
        consoleView.printAllTimes(reservationTimeService.findTimes());
    }
}
