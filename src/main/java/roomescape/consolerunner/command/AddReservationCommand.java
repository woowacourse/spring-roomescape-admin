package roomescape.consolerunner.command;

import roomescape.controller.console.ReservationConsoleController;
import roomescape.controller.console.ReservationTimeConsoleController;
import roomescape.dto.ReservationRequestDto;
import roomescape.view.console.ConsoleInputView;
import roomescape.view.console.ConsoleOutputView;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AddReservationCommand implements ConsoleCommand {

    private static final String COMMAND_INPUT = "5";

    public AddReservationCommand(String input) {
        if (!COMMAND_INPUT.equals(input)) {
            throw new IllegalArgumentException("잘못된 명령어 호출입니다. " + input);
        }
    }

    @Override
    public void conduct(ReservationConsoleController reservationConsoleController,
                        ReservationTimeConsoleController reservationTimeConsoleController,
                        ConsoleInputView consoleInputView,
                        ConsoleOutputView consoleOutputView) {
        try {
            String name = getName(consoleInputView, consoleOutputView);
            LocalDate date = getDate(consoleInputView, consoleOutputView);
            consoleOutputView.printMessage("가능한 시간 목록: ");
            consoleOutputView.printCollection(reservationTimeConsoleController.getReservationTimes());
            Long id = getId(consoleInputView, consoleOutputView);
            consoleOutputView.printResult(
                    reservationConsoleController.postReservation(
                            new ReservationRequestDto(name, date, id)
                    )
            );
        } catch (DateTimeParseException de) {
            consoleOutputView.printErrorMessage("잘못된 날짜 입력입니다.");
        } catch (NumberFormatException ne) {
            consoleOutputView.printErrorMessage("ERROR: 잘못된 id 입력입니다.");
        }
    }

    private String getName(ConsoleInputView consoleInputView, ConsoleOutputView consoleOutputView) {
        consoleOutputView.printMessage("예약자 이름을 입력해주세요.");
        return consoleInputView.getInput();
    }

    private LocalDate getDate(ConsoleInputView consoleInputView, ConsoleOutputView consoleOutputView) {
        consoleOutputView.printMessage("예약 날짜를 입력해주세요.(형식: YYYY-MM-DD)");
        return LocalDate.parse(consoleInputView.getInput());
    }

    private Long getId(ConsoleInputView consoleInputView, ConsoleOutputView consoleOutputView) {
        consoleOutputView.printMessage("예약 시간 id를 입력해주세요.");
        return Long.parseLong(consoleInputView.getInput());
    }
}
