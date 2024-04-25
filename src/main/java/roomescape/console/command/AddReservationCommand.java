package roomescape.console.command;

import org.springframework.stereotype.Component;
import roomescape.console.ConsoleInputScanner;
import roomescape.controller.ReservationApiController;
import roomescape.controller.ReservationTimeApiController;
import roomescape.dto.ReservationRequestDto;
import java.time.LocalDate;

@Component
public class AddReservationCommand implements ConsoleCommand {

    private final ReservationApiController reservationApiController;
    private final ReservationTimeApiController reservationTimeApiController;
    private final ConsoleInputScanner consoleInputScanner;

    public AddReservationCommand(ReservationApiController reservationApiController, ConsoleInputScanner consoleInputScanner, ReservationTimeApiController reservationTimeApiController) {
        this.reservationApiController = reservationApiController;
        this.consoleInputScanner = consoleInputScanner;
        this.reservationTimeApiController = reservationTimeApiController;
    }

    @Override
    public void conduct() {
        System.out.println("예약자 이름을 입력해주세요.");
        String name = consoleInputScanner.getInput();
        System.out.println("예약 날짜를 입력해주세요.(형식: YYYY-MM-DD)");
        LocalDate date = LocalDate.parse(consoleInputScanner.getInput());
        System.out.println(reservationTimeApiController.getReservationTimes());
        System.out.println("예약 시간 id를 입력해주세요.");
        Long id = Long.parseLong(consoleInputScanner.getInput());

        System.out.println(
                reservationApiController.postReservation(
                        new ReservationRequestDto(name, date, id)
                )
        );
    }
}
