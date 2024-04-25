package roomescape.console.command;

import org.springframework.stereotype.Component;
import roomescape.console.ConsoleInputScanner;
import roomescape.controller.ReservationApiController;

@Component
public class DeleteReservationCommand implements ConsoleCommand {

    private final ReservationApiController reservationApiController;
    private final ConsoleInputScanner consoleInputScanner;

    public DeleteReservationCommand(ReservationApiController reservationApiController, ConsoleInputScanner consoleInputScanner) {
        this.reservationApiController = reservationApiController;
        this.consoleInputScanner = consoleInputScanner;
    }

    @Override
    public void conduct() {
        System.out.println(reservationApiController.getReservations());
        System.out.println("삭제할 예약 id를 입력해주세요.");
        Long id = Long.parseLong(consoleInputScanner.getInput());
        System.out.println(reservationApiController.deleteReservation(id));
    }
}
