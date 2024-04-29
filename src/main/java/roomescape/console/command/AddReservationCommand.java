package roomescape.console.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.springframework.stereotype.Component;
import roomescape.console.ConsoleInputView;
import roomescape.console.ConsoleOutputView;
import roomescape.controller.ReservationApiController;
import roomescape.controller.ReservationTimeApiController;
import roomescape.dto.ReservationRequestDto;

@Component
public class AddReservationCommand implements ConsoleCommand {

    private final ReservationApiController reservationApiController;
    private final ReservationTimeApiController reservationTimeApiController;
    private final ConsoleInputView consoleInputView;
    private final ConsoleOutputView consoleOutputView;

    public AddReservationCommand(ReservationApiController reservationApiController,
                                 ReservationTimeApiController reservationTimeApiController,
                                 ConsoleInputView consoleInputView,
                                 ConsoleOutputView consoleOutputView) {
        this.reservationApiController = reservationApiController;
        this.reservationTimeApiController = reservationTimeApiController;
        this.consoleInputView = consoleInputView;
        this.consoleOutputView = consoleOutputView;
    }

    @Override
    public void conduct() {
        try {
            String name = getName();
            LocalDate date = getDate();
            Long id = getId();
            consoleOutputView.printResult(
                    reservationApiController.postReservation(
                            new ReservationRequestDto(name, date, id)
                    )
            );
        } catch (DateTimeParseException de) {
            consoleOutputView.printErrorMessage("잘못된 날짜 입력입니다.");
        } catch (NumberFormatException ne) {
            consoleOutputView.printErrorMessage("ERROR: 잘못된 id 입력입니다.");
        }
    }

    private String getName() {
        consoleOutputView.printMessage("예약자 이름을 입력해주세요.");
        return consoleInputView.getInput();
    }

    private LocalDate getDate() {
        consoleOutputView.printMessage("예약 날짜를 입력해주세요.(형식: YYYY-MM-DD)");
        return LocalDate.parse(consoleInputView.getInput());
    }

    private Long getId() {
        System.out.println(reservationTimeApiController.getReservationTimes());
        consoleOutputView.printMessage("예약 시간 id를 입력해주세요.");
        return Long.parseLong(consoleInputView.getInput());
    }
}
