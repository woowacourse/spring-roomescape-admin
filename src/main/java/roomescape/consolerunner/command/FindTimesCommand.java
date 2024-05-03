package roomescape.consolerunner.command;

import roomescape.controller.console.ReservationConsoleController;
import roomescape.controller.console.ReservationTimeConsoleController;
import roomescape.view.console.ConsoleInputView;
import roomescape.view.console.ConsoleOutputView;

public class FindTimesCommand implements ConsoleCommand {

    private static final String COMMAND_INPUT = "1";

    public FindTimesCommand(String input) {
        if (!COMMAND_INPUT.equals(input)) {
            throw new IllegalArgumentException("잘못된 명령어 호출입니다. " + input);
        }
    }

    @Override
    public void conduct(ReservationConsoleController reservationConsoleController,
                        ReservationTimeConsoleController reservationTimeConsoleController,
                        ConsoleInputView consoleInputView,
                        ConsoleOutputView consoleOutputView) {
        consoleOutputView.printCollection(reservationTimeConsoleController.getReservationTimes());

    }
}
