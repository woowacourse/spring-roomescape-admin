package roomescape.consoleview.command;

import java.util.Arrays;
import java.util.List;

public class Command {

    private final CommandType type;
    private final List<String> arguments;

    private Command(CommandType type, List<String> arguments) {
        this.type = type;
        this.arguments = arguments;
    }

    public static Command from(String rawCommand) {
        return new Command(findCommandType(rawCommand), findArguments(rawCommand));
    }

    private static CommandType findCommandType(String command) {
        String prefix = command.split(" ")[0];
        return Arrays.stream(CommandType.values())
            .filter(value -> prefix.equals(value.getPrefix()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 명령어입니다."));
    }

    private static List<String> findArguments(String command) {
        List<String> arguments = Arrays.asList(command.split(" "));
        return arguments.subList(1, arguments.size());
    }

    public CommandType type() {
        return type;
    }

    public String argumentOf(int index) {
        try {
            return arguments.get(index);
        } catch (IndexOutOfBoundsException exception) {
            throw new IllegalArgumentException("명령어의 인자 수가 맞지 않습니다. 명령어 목록: help");
        }
    }
}
