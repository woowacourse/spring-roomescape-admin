package roomescape.console.controller;


import java.util.List;
import org.springframework.stereotype.Controller;
import roomescape.general.dto.ReservationResponse;
import roomescape.general.dto.ReservationTimeResponse;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;

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
        int read = inputView.readInt();
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
                case 4 -> {
                    List<ReservationResponse> all = consoleReservationController.findAll();
                    outputView.printReservations(all);
                }
                case 5 -> {
                    consoleReservationController.add(CommandConverter.convertPostReservation(inputView.readCreateReservation()));
                }
                case 6 -> {
                    consoleReservationController.delete(inputView.readReservationId());
                }
            }
            outputView.printMainScreen();
            read = inputView.readInt();
        }
    }
}
