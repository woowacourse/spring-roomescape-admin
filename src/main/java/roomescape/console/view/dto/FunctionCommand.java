package roomescape.console.view.dto;

import java.util.Arrays;

public enum FunctionCommand {

    FIND_ALL(1),
    CREATE(2),
    DELETE(3),
    ;

    private final int inputCommand;

    FunctionCommand(int inputCommand) {
        this.inputCommand = inputCommand;
    }

    public static FunctionCommand from(int inputCommand) {
        return Arrays.stream(FunctionCommand.values())
                .filter(command -> command.isSameCommand(inputCommand))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("입력 형식이 일치하지 않습니다."));
    }

    private boolean isSameCommand(int inputCommand) {
        return this.inputCommand == inputCommand;
    }

    public boolean isFindAll() {
        return this == FIND_ALL;
    }

    public boolean isCreate() {
        return this == CREATE;
    }

    public boolean isDelete() {
        return this == DELETE;
    }

}
