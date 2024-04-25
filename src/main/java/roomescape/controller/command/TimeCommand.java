package roomescape.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.service.ReservationTimeService;
import roomescape.view.ConsoleView;

@Component
public abstract class TimeCommand extends Command{

    protected final ReservationTimeService reservationTimeService;

    @Autowired
    public TimeCommand(ConsoleView consoleView, ReservationTimeService reservationTimeService) {
        super(consoleView);
        this.reservationTimeService = reservationTimeService;
    }
}
