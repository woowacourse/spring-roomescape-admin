package roomescape.console.utils;

import java.util.Arrays;

public enum ConsoleCommand {
    TIMES("times"),
    RESERVATIONS("reservations");

    private final String value;

    ConsoleCommand(final String value) {
        this.value = value;
    }

    public static ConsoleCommand from(String value) {
        return Arrays.stream(ConsoleCommand.values())
                .filter(consoleCommand -> consoleCommand.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("'" + value + "'는 존재하지 않는 명령어입니다."));
    }
}
