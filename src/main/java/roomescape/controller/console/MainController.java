package roomescape.controller.console;

import java.util.Map;
import org.springframework.stereotype.Controller;
import roomescape.controller.console.command.Command;
import roomescape.controller.console.command.CommandExecutor;
import roomescape.view.CommandView;

@Controller
public class MainController {
    private final CommandView commandView = new CommandView();
    private final Map<Command, CommandExecutor> commands;

    public MainController(
            final ReservationTimeConsoleController reservationTimeConsoleController,
            final ReservationConsoleController reservationConsoleController
    ) {
        this.commands = Map.of(
                new Command(1), reservationTimeConsoleController::saveTime,
                new Command(2), reservationTimeConsoleController::deleteTime,
                new Command(3), reservationTimeConsoleController::getTimes,
                new Command(4), reservationConsoleController::saveReservation,
                new Command(5), reservationConsoleController::deleteReservation,
                new Command(6), reservationConsoleController::getReservation
        );
    }

    public void run() {
        Command command = commandView.readCommand();
        while (!command.equals(new Command(0))) {
            commands.get(command).execute();
            command = commandView.readCommand();
        }
    }
}
