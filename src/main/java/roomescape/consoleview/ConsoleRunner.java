package roomescape.consoleview;

import static roomescape.consoleview.command.CommandType.CREATE;
import static roomescape.consoleview.command.CommandType.DELETE;
import static roomescape.consoleview.command.CommandType.HELP;
import static roomescape.consoleview.command.CommandType.SHOW;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import roomescape.consoleview.command.Command;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;
import roomescape.controller.dto.SaveReservationRequest;
import roomescape.controller.dto.SaveReservationTimeRequest;

@Profile("default")
@Component
public class ConsoleRunner implements ApplicationRunner {

    private final InputView inputView;
    private final OutputView outputView;
    private final ReservationController reservationController;
    private final ReservationTimeController reservationTimeController;

    public ConsoleRunner(
        InputView inputView,
        OutputView outputView,
        ReservationController reservationController,
        ReservationTimeController reservationTimeController) {

        this.inputView = inputView;
        this.outputView = outputView;
        this.reservationController = reservationController;
        this.reservationTimeController = reservationTimeController;
    }

    @Override
    public void run(ApplicationArguments args) {
        while (true) {
            try {
                Command command = Command.from(inputView.readCommand());
                execute(command);
            } catch (RuntimeException exception) {
                outputView.printError("올바른 명령어를 입력해 주세요.");
            }
        }
    }

    private void execute(Command command) {
        if (command.type() == HELP) {
            outputView.printHelp();
        }
        if (command.type() == SHOW) {
            if (command.argumentOf(0).equals("reservation")) {
                outputView.printReservations(reservationController.findAll());
            }
            if (command.argumentOf(0).equals("time")) {
                outputView.printTimes(reservationTimeController.findAll());
            }
        }
        if (command.type() == CREATE) {
            if (command.argumentOf(0).equals("reservation")) {
                String name = command.argumentOf(1);
                String date = command.argumentOf(2);
                Long timeId = Long.parseLong(command.argumentOf(3));
                reservationController.save(new SaveReservationRequest(date, name, timeId));
            }
            if (command.argumentOf(0).equals("time")) {
                String startAt = command.argumentOf(1);
                reservationTimeController.save(new SaveReservationTimeRequest(startAt));
            }
        }
        if (command.type() == DELETE) {
            if (command.argumentOf(0).equals("reservation")) {
                long reservationId = Long.parseLong(command.argumentOf(1));
                reservationController.delete(reservationId);
            }
            if (command.argumentOf(0).equals("time")) {
                long timeId = Long.parseLong(command.argumentOf(1));
                reservationTimeController.delete(timeId);
            }
        }
    }
}
