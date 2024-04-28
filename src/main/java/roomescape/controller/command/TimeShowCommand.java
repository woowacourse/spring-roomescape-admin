package roomescape.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.service.ReservationTimeService;
import roomescape.view.ConsoleView;

@Component
public class TimeShowCommand extends TimeCommand {

    @Autowired
    public TimeShowCommand(ConsoleView consoleView, ReservationTimeService reservationTimeService) {
        super(consoleView, reservationTimeService);
    }

    @Override
    public void execute() {
        consoleView.printAllTimes(reservationTimeService.findTimes());
    }
}
