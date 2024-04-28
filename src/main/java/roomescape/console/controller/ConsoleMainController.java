package roomescape.console.controller;


import java.util.Arrays;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import roomescape.general.dto.ReservationResponse;
import roomescape.general.dto.ReservationTimeResponse;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;

@Controller
public class ConsoleMainController implements CommandLineRunner {

    private final InputView inputView;
    private final OutputView outputView;
    private final ConsoleReservationController consoleReservationController;
    private final ConsoleReservationTimeController consoleReservationTimeController;
    private final ApplicationContext applicationContext;

    public ConsoleMainController(final InputView inputView, final OutputView outputView,
                                 final ConsoleReservationController consoleReservationController,
                                 final ConsoleReservationTimeController consoleReservationTimeController,
                                 final ApplicationContext applicationContext) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.consoleReservationController = consoleReservationController;
        this.consoleReservationTimeController = consoleReservationTimeController;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(final String... args) {
        while (true) {
            try {
                outputView.printMainScreen();
                int command = inputView.readInt();
                if (command == 0) {
                    break;
                }
                handleCommand(command);
            } catch (IllegalArgumentException e) {
                outputView.print(e.getMessage());
            }
        }
    }

    private void handleCommand(final int command) {
        switch (command) {
            case 0 -> SpringApplication.exit(applicationContext, () -> 0);
            case 1 -> outputView.printReservationTimes(consoleReservationTimeController.findAll());
            case 2 -> handleCreateReservationTime();
            case 3 -> consoleReservationTimeController.delete(inputView.readTimeId());
            case 4 -> outputView.printReservations(consoleReservationController.findAll());
            case 5 -> consoleReservationController.add(Arrays.stream(inputView.readCreateReservation().split("/"))
                    .map(String::trim)
                    .toList());
            case 6 -> consoleReservationController.delete(inputView.readReservationId());
            default -> throw new IllegalArgumentException("올바른 명령어가 아닙니다");
        }
    }

    private void handleCreateReservationTime() {
        consoleReservationTimeController.add(inputView.readTime());
        outputView.printCreateReservationTimeResult();
    }

}
