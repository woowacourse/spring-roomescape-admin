package roomescape.console.command;

import java.time.format.DateTimeParseException;
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
        try {
            String name = getName();
            LocalDate date = getDate();
            Long id = getId();
            System.out.println(
                    reservationApiController.postReservation(
                            new ReservationRequestDto(name, date, id)
                    )
            );
        } catch (DateTimeParseException de) {
            System.out.println("ERROR: 잘못된 날짜 입력입니다.");
        } catch (NumberFormatException ne) {
            System.out.println("ERROR: 잘못된 id 입력입니다.");
        }
    }

    private String getName() {
        System.out.println("예약자 이름을 입력해주세요.");
        return consoleInputScanner.getInput();
    }

    private LocalDate getDate() {
        System.out.println("예약 날짜를 입력해주세요.(형식: YYYY-MM-DD)");
        return LocalDate.parse(consoleInputScanner.getInput());
    }

    private Long getId() {
        System.out.println(reservationTimeApiController.getReservationTimes());
        System.out.println("예약 시간 id를 입력해주세요.");
        return Long.parseLong(consoleInputScanner.getInput());
    }
}
