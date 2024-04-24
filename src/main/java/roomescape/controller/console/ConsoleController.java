package roomescape.controller.console;

import java.util.EnumMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import roomescape.view.InputView;
import roomescape.view.OutputView;
import roomescape.view.command.AdminCommand;

@Controller
public class ConsoleController {

    private final ReservationConsoleController reservationController;
    private final TimeSlotConsoleController timeSlotController;
    private final Map<AdminCommand, CommandExecutor> commandExecutors;

    public ConsoleController(ReservationConsoleController reservationController,
                             TimeSlotConsoleController timeSlotController) {
        this.reservationController = reservationController;
        this.timeSlotController = timeSlotController;
        this.commandExecutors = new EnumMap<>(AdminCommand.class);
    }

    public void run() {
        prepareCommandExecutors();
        AdminCommand command;
        do {
            OutputView.printAdminMenu();
            command = InputView.getAdminCommand();
            commandExecutors.get(command).execute();
        } while (!command.isExit());
    }

    private void prepareCommandExecutors() {
        commandExecutors.putAll(Map.of(
                AdminCommand.RESERVATION_MANAGEMENT, reservationController::main,
                AdminCommand.TIME_SLOT_MANAGEMENT, timeSlotController::main,
                AdminCommand.EXIT, () -> {}
        ));
    }
}
