package roomescape.view;

import java.util.Arrays;

public enum Command {

    TIME_ADD("1"),
    TIME_GET("2"),
    TIME_DELETE("3"),
    RESERVATION_ADD("4"),
    RESERVATION_GET("5"),
    RESERVATION_DELETE("6"),
    QUIT("7");

    private final String value;

    Command(String value) {
        this.value = value;
    }

    public static Command find(String value) {
        return Arrays.stream(values()).filter(command -> command.value.equals(value)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 명령입니다."));
    }
}
