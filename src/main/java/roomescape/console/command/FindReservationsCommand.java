package roomescape.console.command;

import org.springframework.stereotype.Component;
import roomescape.console.ConsoleOutputView;
import roomescape.controller.ReservationApiController;

@Component
public class FindReservationsCommand implements ConsoleCommand {

    private final ReservationApiController reservationApiController;
    private final ConsoleOutputView consoleOutputView;

    public FindReservationsCommand(ReservationApiController reservationApiController,
                                   ConsoleOutputView consoleOutputView) {
        this.reservationApiController = reservationApiController;
        this.consoleOutputView = consoleOutputView;
    }

    @Override
    public void conduct() {
        consoleOutputView.printCollection(reservationApiController.getReservations());
    }
}
