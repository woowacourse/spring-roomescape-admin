package roomescape.controller.command;

import roomescape.service.ReservationService;
import roomescape.view.ConsoleView;

public class ReservationDeleteCommand extends ReservationCommand {

    public ReservationDeleteCommand(ConsoleView consoleView, ReservationService reservationService) {
        super(consoleView, reservationService);
    }

    @Override
    public void execute() {
        long id = consoleView.readReservationToDelete();
        reservationService.deleteReservation(id);
    }
}
