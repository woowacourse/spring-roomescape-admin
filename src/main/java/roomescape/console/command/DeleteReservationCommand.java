package roomescape.console.command;

import org.springframework.stereotype.Component;
import roomescape.console.ConsoleInputView;
import roomescape.console.ConsoleOutputView;
import roomescape.controller.ReservationApiController;

@Component
public class DeleteReservationCommand implements ConsoleCommand {

    private final ReservationApiController reservationApiController;
    private final ConsoleInputView consoleInputView;
    private final ConsoleOutputView consoleOutputView;

    public DeleteReservationCommand(ReservationApiController reservationApiController, ConsoleInputView consoleInputView,
                                    ConsoleOutputView consoleOutputView) {
        this.reservationApiController = reservationApiController;
        this.consoleInputView = consoleInputView;
        this.consoleOutputView = consoleOutputView;
    }

    @Override
    public void conduct() {
        consoleOutputView.printCollection(reservationApiController.getReservations());
        consoleOutputView.printMessage("삭제할 예약 id를 입력해주세요.");
        Long id = Long.parseLong(consoleInputView.getInput());
        consoleOutputView.printResult(reservationApiController.deleteReservation(id));
    }
}
