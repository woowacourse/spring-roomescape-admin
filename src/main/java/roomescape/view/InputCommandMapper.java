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

        SAVE_TIME("1", TimeSaveCommand.class),
        DELETE_TIME("2", TimeDeleteCommand.class),
        SAVE_RESERVATION("3", ReservationSaveCommand.class),
        DELETE_RESERVATION("4", ReservationDeleteCommand.class),
        EXIT("5", ExitCommand.class),
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