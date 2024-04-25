package roomescape.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.service.ReservationTimeService;
import roomescape.view.ConsoleView;

@Component
public class TimeDeleteCommand extends TimeCommand {

    @Autowired
    public TimeDeleteCommand(ConsoleView consoleView, ReservationTimeService reservationTimeService) {
        super(consoleView, reservationTimeService);
    }

    @Override
    public void execute() {
        consoleView.printAllTimes(reservationTimeService.findTimes());
        long id = consoleView.readTimeToDelete();
        reservationTimeService.deleteTime(id);
    }
}
