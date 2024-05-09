package roomescape.consoleview.command;

import java.util.List;
import roomescape.consoleview.OutputView;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;

public class IllegalCommand implements Command {

    @Override
    public void execute(List<String> arguments, ReservationController reservationController,
        ReservationTimeController reservationTimeController) {

        OutputView.printError("존재하지 않는 명령어입니다. 명령어 목록: help");
    }
}
