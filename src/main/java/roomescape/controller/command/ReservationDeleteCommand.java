package roomescape.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.service.ReservationService;
import roomescape.view.ConsoleView;

@Component
public class ReservationDeleteCommand extends ReservationCommand{

    @Autowired
    public ReservationDeleteCommand(ConsoleView consoleView, ReservationService reservationService) {
        super(consoleView, reservationService);
    }

    @Override
    public void execute() {
        long id = consoleView.readReservationToDelete();
        reservationService.deleteReservation(id);
    }
}
