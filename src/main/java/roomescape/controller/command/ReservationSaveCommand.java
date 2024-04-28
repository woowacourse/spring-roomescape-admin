package roomescape.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationRequestDto;
import roomescape.service.ReservationService;
import roomescape.view.ConsoleView;

@Component
public class ReservationSaveCommand extends ReservationCommand{

    @Autowired
    public ReservationSaveCommand(ConsoleView consoleView, ReservationService reservationService) {
        super(consoleView, reservationService);
    }

    @Override
    public void execute() {
        ReservationRequestDto reservationRequestDto = consoleView.readReservationToAdd();
        reservationService.addReservation(reservationRequestDto);
    }
}
