package roomescape.console.controller;


import java.util.Arrays;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
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

    public ConsoleMainController(final OutputView outputView, final InputView inputView,
                                 final ConsoleReservationController consoleReservationController,
                                 final ConsoleReservationTimeController consoleReservationTimeController) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.consoleReservationController = consoleReservationController;
        this.consoleReservationTimeController = consoleReservationTimeController;
    }

    @Override
    public void run(final String... args) {
        outputView.printMainScreen();
        int command = inputView.readInt();
        while (command != 0) {
            handleCommand(command);
            outputView.printMainScreen();
            command = inputView.readInt();
        }
    }

    private void handleCommand(final int command) {
        switch (command) {
            case 1 -> outputView.printReservationTimes(consoleReservationTimeController.findAll());
            case 2 -> handleCreateReservationTime();
            case 3 -> consoleReservationTimeController.delete(inputView.readTimeId());
            case 4 -> outputView.printReservations(consoleReservationController.findAll());
            case 5 -> consoleReservationController.add(Arrays.stream(inputView.readCreateReservation().split("/"))
                    .map(String::trim)
                    .toList());
            case 6 -> consoleReservationController.delete(inputView.readReservationId());
        }
    }

    private void handleCreateReservationTime() {
        consoleReservationTimeController.add(inputView.readTime());
        outputView.printCreateReservationTimeResult();
    }

}
