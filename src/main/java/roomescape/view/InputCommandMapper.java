package roomescape.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import roomescape.controller.command.*;

import java.util.Arrays;

@Component
public class InputCommandMapper {

    private final ApplicationContext applicationContext;

    @Autowired
    public InputCommandMapper(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Command findCommand(String input) {
        Class<? extends Command> classType = InputCommand.findCommand(input);
        return applicationContext.getBean(classType);
    }

    private enum InputCommand {

        SHOW_TIME("1", TimeShowCommand.class),
        SAVE_TIME("2", TimeSaveCommand.class),
        DELETE_TIME("3", TimeDeleteCommand.class),
        SHOW_RESERVATION("4", ReservationShowCommand.class),
        SAVE_RESERVATION("5", ReservationSaveCommand.class),
        DELETE_RESERVATION("6", ReservationDeleteCommand.class),
        EXIT("7", ExitCommand.class),
        ;

        private final String input;
        private final Class<? extends Command> commandType;

        InputCommand(String input, Class<? extends Command> commandType) {
            this.input = input;
            this.commandType = commandType;
        }

        public static Class<? extends Command> findCommand(String input) {
            return Arrays.stream(values())
                    .filter(value -> value.input.equals(input))
                    .findFirst()
                    .orElseThrow()
                    .commandType;
        }
    }
}