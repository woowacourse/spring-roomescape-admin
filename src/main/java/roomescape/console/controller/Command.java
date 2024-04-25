package roomescape.console.controller;

import java.util.List;
import roomescape.console.view.CommandType;

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
