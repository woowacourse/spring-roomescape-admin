package roomescape.console;

import java.util.Arrays;

public enum Command {
    RESERVATION_GET("reservations/get"),
    RESERVATION_POST("reservations/post"),
    RESERVATION_DELETE("reservations/delete"),
    RESERVATION_TIME_GET("reservation_times/get"),
    RESERVATION_TIME_POST("reservation_times/post"),
    RESERVATION_TIME_DELETE("reservation_times/delete"),
    EXIT("exit");

    private final String value;

    Command(final String value) {
        this.value = value;
    }

    public static Command findBy(String command) {
        return Arrays.stream(values())
                .filter(v -> v.value.equals(command))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("invalid command: " + command));
    }
}
