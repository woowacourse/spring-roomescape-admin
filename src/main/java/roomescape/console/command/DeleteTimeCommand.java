package roomescape.console.command;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import roomescape.console.ConsoleInputScanner;
import roomescape.controller.ReservationTimeApiController;

@Component
public class DeleteTimeCommand implements ConsoleCommand {

    private final ReservationTimeApiController reservationTimeApiController;
    private final ConsoleInputScanner consoleInputScanner;

    public DeleteTimeCommand(ReservationTimeApiController reservationTimeApiController, ConsoleInputScanner consoleInputScanner) {
        this.reservationTimeApiController = reservationTimeApiController;
        this.consoleInputScanner = consoleInputScanner;
    }

    @Override
    public void conduct() {
        System.out.println(reservationTimeApiController.getReservationTimes());
        System.out.print("삭제할 시간 id를 입력해주세요: ");
        String input = consoleInputScanner.getInput();
        try {
            Long id = Long.parseLong(input);
            System.out.println(reservationTimeApiController.deleteReservationTime(id));
        } catch (NumberFormatException e) {
            System.out.println("ERROR: 잘못된 id 형식입니다." + input);
        } catch (DataIntegrityViolationException e) {
            System.out.println("ERROR: 현재 예약이 되어있는 시간입니다.");
        }
    }
}
