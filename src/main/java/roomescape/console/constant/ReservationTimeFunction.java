package roomescape.console.constant;

import java.util.Arrays;
import java.util.List;
import roomescape.exception.InvalidFunctionException;

public enum ReservationTimeFunction {

    ADD_RESERVATION_TIME(1),
    GET_RESERVATION_TIMES(2),
    DELETE_RESERVATION_TIME(3),
    ;

    private static final List<ReservationTimeFunction> functions = List.of(
            ADD_RESERVATION_TIME, GET_RESERVATION_TIMES, DELETE_RESERVATION_TIME
    );

    private final int value;

    ReservationTimeFunction(int value) {
        this.value = value;
    }

    public static ReservationTimeFunction getReservationTimeFunction(int value) {
        return Arrays.stream(values())
                .filter(function -> value == function.getValue())
                .findAny()
                .orElseThrow(() -> new InvalidFunctionException("잘못된 기능 선택입니다."));
    }

    private int getValue() {
        return value;
    }
}
