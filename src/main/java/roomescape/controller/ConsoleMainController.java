package roomescape.controller;


import java.util.List;
import org.springframework.stereotype.Controller;
import roomescape.dto.ReservationTimeResponse;
import roomescape.view.InputView;
import roomescape.view.OutputView;

@Controller
public class ConsoleMainController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ConsoleReservationController consoleReservationController;
    private final ConsoleReservationTimeController consoleReservationTimeController;

    public ConsoleMainController(
            final OutputView outputView,
            final InputView inputView,
            final ConsoleReservationController consoleReservationController,
            final ConsoleReservationTimeController consoleReservationTimeController
    ) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.consoleReservationController = consoleReservationController;
        this.consoleReservationTimeController = consoleReservationTimeController;
    }

    public void run() {
        outputView.printMainScreen();
        int read = inputView.read();
        while (read != 0) {
            switch (read) {
                case 1 -> {
                    List<ReservationTimeResponse> all = consoleReservationTimeController.findAll();
                    outputView.printReservationTimes(all);
                }
                case 2 -> {
                    consoleReservationTimeController.add(inputView.readTime());
                    outputView.printCreateReservationTimeResult();
                }
                case 3 -> consoleReservationTimeController.delete(inputView.readTimeId());
            }
            outputView.printMainScreen();
            read = inputView.read();
        }
    }
}
