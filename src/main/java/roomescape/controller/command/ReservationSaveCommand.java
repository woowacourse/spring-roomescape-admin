package roomescape.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationRequestDto;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.ConsoleView;

@Component
public class ReservationSaveCommand extends ReservationCommand{

    @Autowired
    public ReservationSaveCommand(ConsoleView consoleView, ReservationService reservationService, ReservationTimeService reservationTimeService) {
        super(consoleView, reservationService, reservationTimeService);
    }

    @Override
    public void execute() {
        consoleView.printAllTimes(reservationTimeService.findTimes());
        consoleView.printAllReservations(reservationService.findReservations());
        ReservationRequestDto reservationRequestDto = consoleView.readReservationToAdd();
        reservationService.addReservation(reservationRequestDto);
    }
}
