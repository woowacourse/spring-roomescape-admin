package roomescape.console.command;

import org.springframework.stereotype.Component;
import roomescape.console.ConsoleOutputView;
import roomescape.controller.ReservationTimeApiController;

@Component
public class FindTimesCommand implements ConsoleCommand {

    private final ReservationTimeApiController reservationTimeApiController;
    private final ConsoleOutputView consoleOutputView;

    public FindTimesCommand(ReservationTimeApiController reservationTimeApiController,
                            ConsoleOutputView consoleOutputView) {
        this.reservationTimeApiController = reservationTimeApiController;
        this.consoleOutputView = consoleOutputView;
    }

    @Override
    public void conduct() {
        consoleOutputView.printCollection(reservationTimeApiController.getReservationTimes());
    }
}
