package roomescape.console.view;

import java.util.Arrays;
import java.util.List;

public record ConsoleCommand(ConsoleCommandType consoleCommandType, List<String> body) {

    public static final String COMMAND_DELIMITER = " ";
    public static final String BODY_DELIMITER = ",";
    public static final int BODY_EXIST_SIZE = 2;
    public static final int BODY_NON_EXIST_SIZE = 1;
    public static final int BODY_INDEX = 1;

    public static ConsoleCommand from(String input) {
        String[] inputs = input.split(COMMAND_DELIMITER);

        ConsoleCommandType command = ConsoleCommandType.from(inputs[0]);
        validateSize(command, inputs);
        List<String> body = parseBody(command, inputs);

        return new ConsoleCommand(command, body);
    }

    private static void validateSize(ConsoleCommandType command, String[] inputs) {
        if (command.hasBody() && inputs.length != BODY_EXIST_SIZE) {
            throw new IllegalArgumentException("잘못된 커맨드입니다.");
        }

        if (!command.hasBody() && inputs.length != BODY_NON_EXIST_SIZE) {
            throw new IllegalArgumentException("잘못된 커맨드입니다.");
        }
    }

    private static List<String> parseBody(ConsoleCommandType command, String[] inputs) {
        if (command.hasBody()) {
            return Arrays.asList(inputs[BODY_INDEX].split(BODY_DELIMITER));
        }
        return List.of();
    }
}
