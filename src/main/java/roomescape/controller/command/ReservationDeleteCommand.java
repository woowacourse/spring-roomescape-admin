package roomescape.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.ConsoleView;

@Component
public class ReservationDeleteCommand extends ReservationCommand{

    @Autowired
    public ReservationDeleteCommand(ConsoleView consoleView, ReservationService reservationService, ReservationTimeService reservationTimeService) {
        super(consoleView, reservationService, reservationTimeService);
    }

    @Override
    public void execute() {
        consoleView.printAllReservations(reservationService.findReservations());
        long id = consoleView.readReservationToDelete();
        reservationService.deleteReservation(id);
    }
}
