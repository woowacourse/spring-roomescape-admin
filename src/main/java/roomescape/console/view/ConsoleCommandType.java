package roomescape.console.view;

import java.util.Arrays;

public enum ConsoleCommandType {
    FIND_RESERVATIONS("GET/reservations", false),
    CREATE_RESERVATION("POST/reservations", true),
    DELETE_RESERVATION("DELETE/reservation", true),
    FIND_RESERVATION_TIMES("GET/times", false),
    CREATE_RESERVATION_TIME("POST/times", true),
    DELETE_RESERVATION_TIME("DELETE/times", true);

    private final String format;
    private final boolean hasBody;

    ConsoleCommandType(String format, boolean hasBody) {
        this.format = format;
        this.hasBody = hasBody;
    }

    public static ConsoleCommandType from(String input) {
        return Arrays.stream(ConsoleCommandType.values())
                .filter(consoleCommandType -> consoleCommandType.format.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 커멘드 입니다."));
    }

    public boolean hasBody() {
        return hasBody;
    }
}
