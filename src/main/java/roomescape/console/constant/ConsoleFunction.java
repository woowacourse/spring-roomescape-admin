package roomescape.console.constant;

import java.util.Arrays;
import roomescape.exception.InvalidFunctionException;

public enum ConsoleFunction {

    RESERVATION(1),
    RESERVATION_TIME(2),
    QUIT(3),
    ;

    private final int value;

    ConsoleFunction(int value) {
        this.value = value;
    }

    public static ConsoleFunction getSystemFunction(int value) {
        return Arrays.stream(values())
                .filter(function -> value == function.getValue())
                .findAny()
                .orElseThrow(() -> new InvalidFunctionException("잘못된 기능 선택입니다."));
    }

    private int getValue() {
        return value;
    }
}
