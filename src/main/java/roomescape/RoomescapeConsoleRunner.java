package roomescape;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Profile;
import roomescape.controller.ReservationConsoleController;
import roomescape.controller.ReservationTimeConsoleController;
import roomescape.view.ConsoleInputView;
import roomescape.view.ConsoleOutputView;
import roomescape.view.ReservationCommand;

@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class})
@Profile("console")
public class RoomescapeConsoleRunner implements CommandLineRunner {
    private final ReservationConsoleController reservationConsoleController;
    private final ReservationTimeConsoleController reservationTimeConsoleController;
    private final ConsoleInputView consoleInputView;
    private final ConsoleOutputView consoleOutputView;

    public static void main(String[] args) {
        SpringApplication.run(RoomescapeConsoleRunner.class, args);
    }

    public RoomescapeConsoleRunner(final ReservationConsoleController reservationConsoleController, final ReservationTimeConsoleController reservationTimeConsoleController, final ConsoleInputView consoleInputView, final ConsoleOutputView consoleOutputView) {
        this.reservationConsoleController = reservationConsoleController;
        this.reservationTimeConsoleController = reservationTimeConsoleController;
        this.consoleInputView = consoleInputView;
        this.consoleOutputView = consoleOutputView;
    }

    @Override
    public void run(final String... args) {
        ReservationCommand command = null;
        while (command == null || !command.isEnd()) {
            try {
                command = consoleInputView.inputCommand();
                processCommand(command);
            } catch (Exception e) {
                consoleInputView.clear();
                consoleOutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void processCommand(final ReservationCommand command) {
        switch (command) {
            case RESERVATION_INQUIRY -> reservationConsoleController.printReservations();
            case RESERVATION_CREATE -> reservationConsoleController.createReservation();
            case RESERVATION_DELETE -> reservationConsoleController.cancelReservation();
            case RESERVATION_TIME_INQUIRY -> reservationTimeConsoleController.printReservationTimes();
            case RESERVATION_TIME_CREATE -> reservationTimeConsoleController.createReservation();
            case RESERVATION_TIME_DELETE -> reservationTimeConsoleController.cancelReservation();
            case CONSOLE_END -> System.exit(0);
            default -> throw new IllegalStateException(String.format("%s에 대한 기능이 없습니다.", command.getMessage()));
        }
    }
}
