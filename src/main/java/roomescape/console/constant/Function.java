package roomescape.console.constant;

import java.util.List;
import roomescape.exception.InvalidFunctionException;

public enum Function {

    RESERVATION(1),
    RESERVATION_TIME(2),
    QUIT(3),

    ADD_RESERVATION(1),
    GET_RESERVATIONS(2),
    DELETE_RESERVATION(3),

    ADD_RESERVATION_TIME(1),
    GET_RESERVATION_TIMES(2),
    DELETE_RESERVATION_TIME(3),
    ;

    private final int value;

    Function(int value) {
        this.value = value;
    }

    public static Function getSystemFunction(int value) {
        List<Function> functions = List.of(RESERVATION, RESERVATION_TIME, QUIT);
        return getFilteredFunction(value, functions);
    }

    public static Function getReservationFunction(int value) {
        List<Function> functions = List.of(ADD_RESERVATION, GET_RESERVATIONS, DELETE_RESERVATION);
        return getFilteredFunction(value, functions);
    }

    public static Function getReservationTimeFunction(int value) {
        List<Function> functions = List.of(ADD_RESERVATION_TIME, GET_RESERVATION_TIMES, DELETE_RESERVATION_TIME);
        return getFilteredFunction(value, functions);
    }

    private static Function getFilteredFunction(int value, List<Function> functions) {
        return functions.stream()
                .filter(function -> value == function.getValue())
                .findAny()
                .orElseThrow(() -> new InvalidFunctionException("잘못된 기능 선택입니다."));
    }

    public int getValue() {
        return value;
    }
}
