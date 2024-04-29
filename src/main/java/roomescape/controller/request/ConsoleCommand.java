package roomescape.controller.request;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum ConsoleCommand {
    FIND_RESERVATIONS(1),
    FIND_RESERVATION_TIMES(2),
    ADD_RESERVATION(3),
    ADD_RESERVATION_TIME(4),
    DELETE_RESERVATION(5),
    DELETE_RESERVATION_TIME(6),
    EXIT(7);

    private final int number;

    ConsoleCommand(int number) {
        this.number = number;
    }

    public static ConsoleCommand getConsoleCommand(int number) {
        return Arrays.stream(values()).filter(consoleCommand -> consoleCommand.number == number)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(number + "에 해당하는 명령어가 없습니다."));
    }

    public boolean isEnd() {
        return this == EXIT;
    }
}
