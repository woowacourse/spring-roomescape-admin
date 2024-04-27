package roomescape.console.utils;

import java.util.Arrays;

public enum ConsoleMethod {
    POST("post"),
    GET("get"),
    DELETE("delete");

    private final String value;

    ConsoleMethod(String value) {
        this.value = value;
    }

    public static ConsoleMethod from(String value) {
        return Arrays.stream(ConsoleMethod.values())
                .filter(consoleMethod -> consoleMethod.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("'" + value + "'는 존재하지 않는 명령어입니다."));
    }
}
