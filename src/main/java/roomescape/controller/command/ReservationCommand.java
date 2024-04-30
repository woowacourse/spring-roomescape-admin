package roomescape.controller.command;

import roomescape.service.ReservationService;
import roomescape.view.ConsoleView;

public abstract class ReservationCommand extends Command {

    protected final ReservationService reservationService;

    public ReservationCommand(ConsoleView consoleView, ReservationService reservationService) {
        super(consoleView);
        this.reservationService = reservationService;
    }
}
