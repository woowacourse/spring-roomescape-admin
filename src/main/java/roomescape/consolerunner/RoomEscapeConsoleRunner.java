package roomescape.consolerunner;

import roomescape.consolerunner.command.ConsoleCommand;
import roomescape.controller.console.ReservationConsoleController;
import roomescape.controller.console.ReservationTimeConsoleController;
import roomescape.view.console.ConsoleInputView;
import roomescape.view.console.ConsoleOutputView;

public class RoomEscapeConsoleRunner {

    private static final String MEMU = """
                            
            필요한 메뉴 번호를 입력해주세요.
            1. 예약 가능 시간 확인
            2. 예약 가능 시간 추가
            3. 예약 가능 시간 삭제
            4. 예약 확인
            5. 예약 추가
            6. 예약 삭제
            """;

    private final ReservationConsoleController reservationConsoleController;
    private final ReservationTimeConsoleController reservationTimeConsoleController;
    private final ConsoleInputView consoleInputView;
    private final ConsoleOutputView consoleOutputView;

    public RoomEscapeConsoleRunner(ReservationConsoleController reservationConsoleController,
                                   ReservationTimeConsoleController reservationTimeConsoleController,
                                   ConsoleInputView consoleInputView,
                                   ConsoleOutputView consoleOutputView) {
        this.reservationConsoleController = reservationConsoleController;
        this.reservationTimeConsoleController = reservationTimeConsoleController;
        this.consoleInputView = consoleInputView;
        this.consoleOutputView = consoleOutputView;
    }

    public void run() {
        while (true) {
            consoleOutputView.printMessage(MEMU);
            String commandInput = consoleInputView.getInput();
            conductCommand(CommandHolder.getCommandFromInput(commandInput));
        }
    }

    private void conductCommand(ConsoleCommand consoleCommand) {
        try {
            consoleCommand.conduct(
                    reservationConsoleController,
                    reservationTimeConsoleController,
                    consoleInputView,
                    consoleOutputView);
        } catch (RuntimeException e) {
            consoleOutputView.printErrorMessage("오류가 발생했습니다: " + e.getMessage());
        }
    }
}
