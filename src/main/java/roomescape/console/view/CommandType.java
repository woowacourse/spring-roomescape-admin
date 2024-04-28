package roomescape.console.view;

import java.util.Arrays;

public enum CommandType {

    POST("post"),
    GET("get"),
    DELETE("delete");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public static CommandType from(String command) {
        return Arrays.stream(CommandType.values())
                .filter(element -> element.command.equalsIgnoreCase(command))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 명령어입니다."));
    }
}
