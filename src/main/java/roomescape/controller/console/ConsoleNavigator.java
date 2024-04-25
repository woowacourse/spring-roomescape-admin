package roomescape.controller.console;

import jakarta.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import roomescape.RoomescapeConsoleApplication;
import roomescape.view.InputView;
import roomescape.view.OutputView;
import roomescape.view.command.AdminCommand;

@Component
@ConditionalOnBean(RoomescapeConsoleApplication.class)
public class ConsoleNavigator {

    private final ReservationConsoleController reservationController;
    private final TimeSlotConsoleController timeSlotController;
    private final Map<AdminCommand, CommandExecutor> commandExecutors;

    public ConsoleNavigator(ReservationConsoleController reservationController,
                            TimeSlotConsoleController timeSlotController) {
        this.reservationController = reservationController;
        this.timeSlotController = timeSlotController;
        this.commandExecutors = new EnumMap<>(AdminCommand.class);
    }

    public void run() {
        AdminCommand command;
        do {
            OutputView.printAdminMenu();
            command = InputView.getAdminCommand();
            commandExecutors.get(command).execute();
        } while (!command.isExit());
    }

    @PostConstruct
    private void prepareCommandExecutors() {
        commandExecutors.putAll(Map.of(
                AdminCommand.RESERVATION_MANAGEMENT, reservationController::menu,
                AdminCommand.TIME_SLOT_MANAGEMENT, timeSlotController::menu,
                AdminCommand.EXIT, () -> {}
        ));
    }

    @Bean
    public CommandLineRunner executeOnStartUp() {
        return args -> this.run();
    }
}
