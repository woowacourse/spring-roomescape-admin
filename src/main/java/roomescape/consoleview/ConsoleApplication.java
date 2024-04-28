package roomescape.consoleview;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import roomescape.consoleview.command.Command;
import roomescape.consoleview.command.CreateCommand;
import roomescape.consoleview.command.DeleteCommand;
import roomescape.consoleview.command.HelpCommand;
import roomescape.consoleview.command.ShowCommand;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;
import roomescape.repository.MemoryReservationRepository;
import roomescape.repository.MemoryReservationTimeRepository;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

public class ConsoleApplication {

    private static final InputView INPUT_VIEW = InputView.getInstance();
    private static final OutputView OUTPUT_VIEW = OutputView.getInstance();

    public static void main(String[] args) {
        while (true) {
            try {
                execute(INPUT_VIEW.readCommand());
            } catch (IllegalArgumentException exception) {
                OUTPUT_VIEW.printError(exception.getMessage());
            }
        }
    }

    private static void execute(String rawCommand) {
        List<String> commands = Arrays.asList(rawCommand.split(" "));
        String prefix = commands.get(0);
        List<String> arguments = commands.subList(1, commands.size());

        findCommand(prefix).execute(
            arguments,
            new ReservationController(new ReservationService(MemoryReservationRepository.getInstance(),
                MemoryReservationTimeRepository.getInstance())),
            new ReservationTimeController(new ReservationTimeService(MemoryReservationTimeRepository.getInstance()))
        );
    }

    private static Command findCommand(String prefix) {
        Map<String, Command> commandMap = Map.of(
            "help", new HelpCommand(),
            "create", new CreateCommand(),
            "show", new ShowCommand(),
            "delete", new DeleteCommand()
        );

        try {
            return commandMap.get(prefix);
        } catch (NullPointerException exception) {
            throw new IllegalArgumentException("존재하지 않는 명령어입니다.");
        }
    }
}
