package roomescape.console.command;

import org.springframework.stereotype.Component;
import roomescape.console.ConsoleInputView;
import roomescape.console.ConsoleOutputView;
import roomescape.controller.ReservationTimeApiController;
import roomescape.dto.ReservationTimeRequestDto;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Component
public class AddTimeCommand implements ConsoleCommand {

    private final ReservationTimeApiController reservationTimeApiController;
    private final ConsoleInputView consoleInputView;
    private final ConsoleOutputView consoleOutputView;

    public AddTimeCommand(ReservationTimeApiController reservationTimeApiController, ConsoleInputView consoleInputView,
                          ConsoleOutputView consoleOutputView) {
        this.reservationTimeApiController = reservationTimeApiController;
        this.consoleInputView = consoleInputView;
        this.consoleOutputView = consoleOutputView;
    }

    @Override
    public void conduct() {
        consoleOutputView.printMessage("추가할 시간을 입력해주세요.(형식 - HH:mm)");
        String time = consoleInputView.getInput();
        try {
            LocalTime startAt = LocalTime.parse(time);
            consoleOutputView.printResult(reservationTimeApiController.postReservationTime(
                    new ReservationTimeRequestDto(startAt)));
        } catch (DateTimeParseException e) {
            consoleOutputView.printErrorMessage("잘못된 시간 형식입니다. " + time);
        }
    }
}
