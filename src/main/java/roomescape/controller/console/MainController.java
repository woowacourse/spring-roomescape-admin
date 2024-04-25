package roomescape.controller.console;

import java.util.Map;
import org.springframework.stereotype.Controller;
import roomescape.controller.console.command.Command;
import roomescape.controller.console.command.CommandExecutor;
import roomescape.view.CommandView;

@Controller
public class MainController {
    private final CommandView commandView = new CommandView();
    private final ReservationTimeConsoleController reservationTimeConsoleController = new ReservationTimeConsoleController();
    private final ReservationConsoleController reservationConsoleController = new ReservationConsoleController();

    private final Map<Command, CommandExecutor> commands = Map.of(
            new Command(1), () -> reservationTimeConsoleController.saveTime(),
            new Command(2), () -> reservationTimeConsoleController.deleteTime(),
            new Command(3), () -> reservationTimeConsoleController.getTimes(),
            new Command(4), () -> reservationConsoleController.saveReservation()
    );

    public void run() {
        Command command = commandView.readCommand();
        while (!command.equals(new Command(0))) {
            commands.get(command).execute();
            command = commandView.readCommand();
        }
    }
}
