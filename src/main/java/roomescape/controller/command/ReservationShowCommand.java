package roomescape.controller.command;

import roomescape.service.ReservationService;
import roomescape.view.ConsoleView;

public class ReservationShowCommand extends ReservationCommand {

    public ReservationShowCommand(ConsoleView consoleView, ReservationService reservationService) {
        super(consoleView, reservationService);
    }

    @Override
    public void execute() {
        consoleView.printAllReservations(reservationService.findReservations());
    }
}
