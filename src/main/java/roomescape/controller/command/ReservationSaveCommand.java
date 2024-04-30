package roomescape.controller.command;

import roomescape.dto.ReservationRequestDto;
import roomescape.service.ReservationService;
import roomescape.view.ConsoleView;

public class ReservationSaveCommand extends ReservationCommand {

    public ReservationSaveCommand(ConsoleView consoleView, ReservationService reservationService) {
        super(consoleView, reservationService);
    }

    @Override
    public void execute() {
        ReservationRequestDto reservationRequestDto = consoleView.readReservationToAdd();
        reservationService.addReservation(reservationRequestDto);
    }
}
