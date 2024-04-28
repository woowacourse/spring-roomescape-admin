package roomescape.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.ConsoleView;

@Component
public class ReservationShowCommand extends ReservationCommand {

    @Autowired
    public ReservationShowCommand(ConsoleView consoleView, ReservationService reservationService, ReservationTimeService reservationTimeService) {
        super(consoleView, reservationService, reservationTimeService);
    }

    @Override
    public void execute() {
        consoleView.printAllReservations(reservationService.findReservations());
    }
}
