package roomescape.console.constant;

import java.util.Arrays;
import roomescape.exception.InvalidFunctionException;

public enum ReservationFunction {

    ADD_RESERVATION(1),
    GET_RESERVATIONS(2),
    DELETE_RESERVATION(3),
    ;

    private final int value;

    ReservationFunction(int value) {
        this.value = value;
    }

    public static ReservationFunction getReservationFunction(int value) {
        return Arrays.stream(values())
                .filter(function -> value == function.getValue())
                .findAny()
                .orElseThrow(() -> new InvalidFunctionException("잘못된 기능 선택입니다."));
    }

    private int getValue() {
        return value;
    }
}
