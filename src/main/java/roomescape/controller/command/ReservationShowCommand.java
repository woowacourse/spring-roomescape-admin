package roomescape.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.service.ReservationService;
import roomescape.view.ConsoleView;

@Component
public class ReservationShowCommand extends ReservationCommand {

    @Autowired
    public ReservationShowCommand(ConsoleView consoleView, ReservationService reservationService) {
        super(consoleView, reservationService);
    }

    @Override
    public void execute() {
        consoleView.printAllReservations(reservationService.findReservations());
    }
}
