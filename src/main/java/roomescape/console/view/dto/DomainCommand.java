package roomescape.console.view.dto;

import java.util.Arrays;

public enum DomainCommand {

    RESERVATION(1),
    RESERVATION_TIME(2),
    END_PROGRAM(3),
    ;

    private final int inputCommand;

    DomainCommand(int inputCommand) {
        this.inputCommand = inputCommand;
    }

    public static DomainCommand from(int inputCommand) {
        return Arrays.stream(DomainCommand.values())
                .filter(command -> command.isSameCommand(inputCommand))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("입력 형식이 일치하지 않습니다."));
    }

    private boolean isSameCommand(int inputCommand) {
        return this.inputCommand == inputCommand;
    }

    public boolean isContinueProgram() {
        return this != END_PROGRAM;
    }

    public boolean isReservationDomain() {
        return this == RESERVATION;
    }

    public boolean isTimeDomain() {
        return this == RESERVATION_TIME;
    }

}
