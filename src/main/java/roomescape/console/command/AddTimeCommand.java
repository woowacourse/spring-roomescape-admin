package roomescape.console.command;

import org.springframework.stereotype.Component;
import roomescape.console.ConsoleInputScanner;
import roomescape.controller.ReservationTimeApiController;
import roomescape.dto.ReservationTimeRequestDto;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Component
public class AddTimeCommand implements ConsoleCommand {

    private final ReservationTimeApiController reservationTimeApiController;
    private final ConsoleInputScanner consoleInputScanner;

    public AddTimeCommand(ReservationTimeApiController reservationTimeApiController, ConsoleInputScanner consoleInputScanner) {
        this.reservationTimeApiController = reservationTimeApiController;
        this.consoleInputScanner = consoleInputScanner;
    }

    @Override
    public void conduct() {
        System.out.println("추가할 시간을 입력해주세요.(형식 - HH:mm)");
        String time = consoleInputScanner.getInput();
        try {
            LocalTime startAt = LocalTime.parse(time);
            System.out.println(reservationTimeApiController.postReservationTime(
                    new ReservationTimeRequestDto(startAt)));
        } catch (DateTimeParseException e) {
            System.out.println("ERROR: 잘못된 시간 형식입니다. " + time);
        }
    }
}
