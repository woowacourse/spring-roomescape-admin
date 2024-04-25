package roomescape.console;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import roomescape.console.command.AddReservationCommand;
import roomescape.console.command.AddTimeCommand;
import roomescape.console.command.ConsoleCommand;
import roomescape.console.command.DeleteReservationCommand;
import roomescape.console.command.DeleteTimeCommand;
import roomescape.console.command.FindReservationsCommand;
import roomescape.console.command.FindTimesCommand;
import roomescape.console.command.WrongInputCommand;
import java.util.Arrays;

@Component
public class ConsoleCommandMatcher {

    private final ApplicationContext applicationContext;

    public ConsoleCommandMatcher(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ConsoleCommand findConsoleCommand(String consoleInput) {
        return applicationContext.getBean(CommandClassMapper.getConsoleCommand(consoleInput));
    }

    private enum CommandClassMapper {
        FIND_TIMES("1", FindTimesCommand.class),
        ADD_TIME("2", AddTimeCommand.class),
        DELETE_TIME("3", DeleteTimeCommand.class),
        FIND_RESERVATIONS("4", FindReservationsCommand.class),
        ADD_RESERVATION("5", AddReservationCommand.class),
        DELETE_RESERVATION("6", DeleteReservationCommand.class),
        WRONG_INPUT("", WrongInputCommand.class);

        private final String input;
        private final Class<? extends ConsoleCommand> consoleCommand;

        CommandClassMapper(String input, Class<? extends ConsoleCommand> consoleCommand) {
            this.input = input;
            this.consoleCommand = consoleCommand;
        }

        private static Class<? extends ConsoleCommand> getConsoleCommand(String input) {
            return Arrays.stream(values())
                    .filter(value -> value.input.equals(input))
                    .findFirst()
                    .orElse(WRONG_INPUT)
                    .consoleCommand;
        }
    }
}
