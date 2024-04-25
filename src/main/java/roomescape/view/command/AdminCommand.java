package roomescape.view.command;

import java.util.Arrays;

public enum AdminCommand {

    RESERVATION_MANAGEMENT("1"),
    TIME_SLOT_MANAGEMENT("2"),
    EXIT("0"),
    ;

    private final String text;

    AdminCommand(String text) {
        this.text = text;
    }

    public boolean isExit() {
        return this == EXIT;
    }

    public static AdminCommand from(String input) {
        return Arrays.stream(values())
                .filter(command -> command.isTextEquals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 명령어입니다."));
    }

    private boolean isTextEquals(String input) {
        return text.equals(input);
    }
}
