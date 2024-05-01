package roomescape.consolerunner.command;

import org.springframework.dao.DataIntegrityViolationException;
import roomescape.controller.console.ReservationConsoleController;
import roomescape.controller.console.ReservationTimeConsoleController;
import roomescape.view.console.ConsoleInputView;
import roomescape.view.console.ConsoleOutputView;

public class DeleteTimeCommand implements ConsoleCommand {

    private static final String COMMAND_INPUT = "6";

    public DeleteTimeCommand(String input) {
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
        consoleOutputView.printMessage("삭제할 시간 id를 입력해주세요: ");
        String input = consoleInputView.getInput();
        try {
            Long id = Long.parseLong(input);
            reservationTimeConsoleController.deleteReservationTime(id);
        } catch (NumberFormatException e) {
            consoleOutputView.printErrorMessage("잘못된 id 형식입니다." + input);
        } catch (DataIntegrityViolationException e) {
            consoleOutputView.printErrorMessage("현재 예약이 되어있는 시간입니다.");
        }
    }
}
