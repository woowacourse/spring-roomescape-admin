package roomescape.console.view;

import java.util.Arrays;

public enum Command {
    FIND_ALL_RESERVATION("1"),
    SAVE_RESERVATION("2"),
    DELETE_RESERVATION("3"),
    FIND_ALL_TIME("4"),
    SAVE_TIME("5"),
    DELETE_TIME("6"),
    END("7");

    private final String input;

    Command(String input) {
        this.input = input;
    }

    private boolean isMatchedWith(String input) {
        return this.input.equals(input);
    }

    public static Command findByInput(String input) {
        return Arrays.stream(Command.values())
                .filter(command -> command.isMatchedWith(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 명령어입니다."));
    }
}
