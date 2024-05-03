package roomescape.consolerunner.command;

import roomescape.controller.console.ReservationConsoleController;
import roomescape.controller.console.ReservationTimeConsoleController;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.view.console.ConsoleInputView;
import roomescape.view.console.ConsoleOutputView;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class AddTimeCommand implements ConsoleCommand {

    private static final String COMMAND_INPUT = "2";

    public AddTimeCommand(String input) {
        if (!COMMAND_INPUT.equals(input)) {
            throw new IllegalArgumentException("잘못된 명령어 호출입니다. " + input);
        }
    }

    @Override
    public void conduct(ReservationConsoleController reservationConsoleController,
                        ReservationTimeConsoleController reservationTimeConsoleController,
                        ConsoleInputView consoleInputView,
                        ConsoleOutputView consoleOutputView) {
        consoleOutputView.printMessage("추가할 시간을 입력해주세요.(형식 - HH:mm)");
        String time = consoleInputView.getInput();
        try {
            LocalTime startAt = LocalTime.parse(time);
            consoleOutputView.printResult(reservationTimeConsoleController.postReservationTime(
                    new ReservationTimeRequestDto(startAt)));
        } catch (DateTimeParseException e) {
            consoleOutputView.printErrorMessage("잘못된 시간 형식입니다. " + time);
        }
    }
}
