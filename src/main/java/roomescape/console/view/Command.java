package roomescape.console.view;

import java.util.List;

public class Command {

    private final CommandType commandType;
    private final String body;

    public Command(List<String> arguments) {
        commandType = CommandType.from(arguments.get(0));
        body = parseBody(arguments);
    }

    private static String parseBody(List<String> arguments) {
        return String.join("", arguments.subList(1, arguments.size()));
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getBody() {
        return body;
    }
}
