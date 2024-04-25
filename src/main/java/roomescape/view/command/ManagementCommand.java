package roomescape.view.command;

import java.util.Arrays;

public enum ManagementCommand {

    CREATE("1"),
    DELETE("2"),
    BACK("0"),
    ;

    private final String text;

    ManagementCommand(String text) {
        this.text = text;
    }

    public boolean isBack() {
        return this == BACK;
    }

    public static ManagementCommand from(String input) {
        return Arrays.stream(values())
                .filter(command -> command.isTextEquals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 명령어입니다."));
    }

    private boolean isTextEquals(String input) {
        return text.equals(input);
    }
}
