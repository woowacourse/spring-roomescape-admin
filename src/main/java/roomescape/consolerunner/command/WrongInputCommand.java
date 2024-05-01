package roomescape.consolerunner.command;

import roomescape.controller.console.ReservationConsoleController;
import roomescape.controller.console.ReservationTimeConsoleController;
import roomescape.view.console.ConsoleInputView;
import roomescape.view.console.ConsoleOutputView;

public class WrongInputCommand implements ConsoleCommand {

    public WrongInputCommand(String input) {
    }

    @Override
    public void conduct(ReservationConsoleController reservationConsoleController, ReservationTimeConsoleController reservationTimeConsoleController, ConsoleInputView consoleInputView, ConsoleOutputView consoleOutputView) {
        consoleOutputView.printErrorMessage("잘못된 명령입니다.");
    }
}
