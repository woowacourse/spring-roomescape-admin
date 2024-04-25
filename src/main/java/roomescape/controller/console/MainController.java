package roomescape.controller.console;

import java.util.Map;
import roomescape.controller.console.command.Command;
import roomescape.controller.console.command.CommandExecutor;
import roomescape.view.CommandView;

public class MainController {
    private final CommandView commandView = new CommandView();
    private final ReservationTimeController reservationTimeController = new ReservationTimeController();
    private final ReservationController reservationController = new ReservationController();

    private final Map<Command, CommandExecutor> commands = Map.of(
            new Command(1), () -> reservationTimeController.saveTime(),
            new Command(2), () -> reservationTimeController.deleteTime(),
            new Command(3), () -> reservationTimeController.getTimes()
    );

    public void run() {
        Command command = commandView.readCommand();
        while (!command.equals(new Command(0))) {
            commands.get(command).execute();
            command = commandView.readCommand();
        }
    }
}
