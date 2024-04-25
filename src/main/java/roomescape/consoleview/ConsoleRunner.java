package roomescape.consoleview;

import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;
import roomescape.controller.dto.SaveReservationTimeRequest;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final Scanner scanner;
    private final ConsoleInputView inputView;
    private final ConsoleOutputView outputView;
    private final ReservationController reservationController;
    private final ReservationTimeController reservationTimeController;

    public ConsoleRunner(
        ConsoleInputView inputView,
        ConsoleOutputView outputView,
        ReservationController reservationController,
        ReservationTimeController reservationTimeController) {

        this.scanner = new Scanner(System.in);
        this.inputView = inputView;
        this.outputView = outputView;
        this.reservationController = reservationController;
        this.reservationTimeController = reservationTimeController;
    }

    @Override
    public void run(String... args) {
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("add time")) {
                reservationTimeController.save(new SaveReservationTimeRequest("10:00"));
                System.out.println("10:00 예약 시간 추가 완료.");
            }
        }
    }
}
