package roomescape.console.view;

import java.util.List;

public class Command {

    private final CommandType commandType;
    private final List<String> body;

    public Command(List<String> arguments) {
        commandType = CommandType.from(arguments.get(0));
        body = parseBody(arguments);
    }

    private static List<String> parseBody(List<String> arguments) {
        return arguments.subList(1, arguments.size());
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public List<String> getBody() {
        return body;
    }
}
