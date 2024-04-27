package roomescape.console.utils;

import java.util.Arrays;

public enum Command {
    TIMES("times"),
    RESERVATIONS("reservations");

    private final String value;

    Command(final String value) {
        this.value = value;
    }

    public static Command from(String value) {
        return Arrays.stream(Command.values())
                .filter(command -> command.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("'" + value + "'는 존재하지 않는 명령어입니다."));
    }
}
