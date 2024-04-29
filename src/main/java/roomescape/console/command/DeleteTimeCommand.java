package roomescape.console.command;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import roomescape.console.ConsoleInputView;
import roomescape.console.ConsoleOutputView;
import roomescape.controller.ReservationTimeApiController;

@Component
public class DeleteTimeCommand implements ConsoleCommand {

    private final ReservationTimeApiController reservationTimeApiController;
    private final ConsoleInputView consoleInputView;
    private final ConsoleOutputView consoleOutputView;

    public DeleteTimeCommand(ReservationTimeApiController reservationTimeApiController, ConsoleInputView consoleInputView,
                             ConsoleOutputView consoleOutputView) {
        this.reservationTimeApiController = reservationTimeApiController;
        this.consoleInputView = consoleInputView;
        this.consoleOutputView = consoleOutputView;
    }

    @Override
    public void conduct() {
        consoleOutputView.printCollection(reservationTimeApiController.getReservationTimes());
        consoleOutputView.printMessage("삭제할 시간 id를 입력해주세요: ");
        String input = consoleInputView.getInput();
        try {
            Long id = Long.parseLong(input);
            consoleOutputView.printResult(reservationTimeApiController.deleteReservationTime(id));
        } catch (NumberFormatException e) {
            consoleOutputView.printErrorMessage("잘못된 id 형식입니다." + input);
        } catch (DataIntegrityViolationException e) {
            consoleOutputView.printErrorMessage("현재 예약이 되어있는 시간입니다.");
        }
    }
}
