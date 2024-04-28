package roomescape.consoleview.command;

import java.util.List;
import roomescape.consoleview.OutputView;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;

public class HelpCommand implements Command {

    @Override
    public void execute(
        List<String> arguments,
        ReservationController reservationController,
        ReservationTimeController reservationTimeController,
        OutputView outputView) {

        outputView.printHelp();
    }
}
