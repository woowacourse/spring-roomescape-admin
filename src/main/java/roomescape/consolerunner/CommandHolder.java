package roomescape.consolerunner;

import roomescape.consolerunner.command.AddReservationCommand;
import roomescape.consolerunner.command.AddTimeCommand;
import roomescape.consolerunner.command.ConsoleCommand;
import roomescape.consolerunner.command.DeleteReservationCommand;
import roomescape.consolerunner.command.DeleteTimeCommand;
import roomescape.consolerunner.command.FindReservationsCommand;
import roomescape.consolerunner.command.FindTimesCommand;
import roomescape.consolerunner.command.WrongInputCommand;
import java.util.Arrays;
import java.util.function.Function;

public class CommandHolder {

    public static ConsoleCommand getCommandFromInput(String input) {
        return CommandMapper.getCommand(input);
    }

    private enum CommandMapper {
        FIND_TIMES("1", FindTimesCommand::new),
        ADD_TIME("2", AddTimeCommand::new),
        DELETE_TIME("3", DeleteTimeCommand::new),
        FIND_RESERVATIONS("4", FindReservationsCommand::new),
        ADD_RESERVATION("5", AddReservationCommand::new),
        DELETE_RESERVATION("6", DeleteReservationCommand::new),
        WRONG_INPUT("7", WrongInputCommand::new)
        ;

        private final String input;
        private final Function<String, ? extends ConsoleCommand> commandMaker;

        CommandMapper(String input, Function<String, ? extends ConsoleCommand> commandMaker) {
            this.input = input;
            this.commandMaker = commandMaker;
        }

        private static ConsoleCommand getCommand(String input) {
            CommandMapper commandMapper = Arrays.stream(values())
                    .filter(value -> value.input.equals(input))
                    .findFirst()
                    .orElse(WRONG_INPUT);
            return commandMapper.commandMaker.apply(commandMapper.input);
        }
    }
}
