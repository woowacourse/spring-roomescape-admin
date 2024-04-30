package roomescape.controller.command;

import roomescape.service.ReservationTimeService;
import roomescape.view.ConsoleView;

public class TimeDeleteCommand extends TimeCommand {

    public TimeDeleteCommand(ConsoleView consoleView, ReservationTimeService reservationTimeService) {
        super(consoleView, reservationTimeService);
    }

    @Override
    public void execute() {
        long id = consoleView.readTimeToDelete();
        reservationTimeService.deleteTime(id);
    }
}
