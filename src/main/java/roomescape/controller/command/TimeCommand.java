package roomescape.controller.command;

import roomescape.service.ReservationTimeService;
import roomescape.view.ConsoleView;

public abstract class TimeCommand extends Command {

    protected final ReservationTimeService reservationTimeService;

    public TimeCommand(ConsoleView consoleView, ReservationTimeService reservationTimeService) {
        super(consoleView);
        this.reservationTimeService = reservationTimeService;
    }
}
