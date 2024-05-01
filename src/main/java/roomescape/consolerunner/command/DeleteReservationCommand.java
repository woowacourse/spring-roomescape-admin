package roomescape.consolerunner.command;

import roomescape.controller.console.ReservationConsoleController;
import roomescape.controller.console.ReservationTimeConsoleController;
import roomescape.view.console.ConsoleInputView;
import roomescape.view.console.ConsoleOutputView;

public class DeleteReservationCommand implements ConsoleCommand {

    private static final String COMMAND_INPUT = "3";

    public DeleteReservationCommand(String input) {
        if (!COMMAND_INPUT.equals(input)) {
            throw new IllegalArgumentException("잘못된 명령어 호출입니다. " + input);
        }
    }

    @Override
    public void conduct(ReservationConsoleController reservationConsoleController,
                        ReservationTimeConsoleController reservationTimeConsoleController,
                        ConsoleInputView consoleInputView,
                        ConsoleOutputView consoleOutputView) {
        consoleOutputView.printCollection(reservationConsoleController.getReservations());
        consoleOutputView.printMessage("삭제할 예약 id를 입력해주세요.");
        Long id = Long.parseLong(consoleInputView.getInput());
        reservationConsoleController.deleteReservation(id);
        consoleOutputView.printMessage("삭제되었습니다.");
    }
}
