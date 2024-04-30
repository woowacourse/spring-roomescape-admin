package roomescape.view;

import roomescape.controller.command.*;

import java.util.Arrays;

enum InputCommandMapper {

    SHOW_TIME("1", TimeShowCommand.class),
    SAVE_TIME("2", TimeSaveCommand.class),
    DELETE_TIME("3", TimeDeleteCommand.class),
    SHOW_RESERVATION("4", ReservationShowCommand.class),
    SAVE_RESERVATION("5", ReservationSaveCommand.class),
    DELETE_RESERVATION("6", ReservationDeleteCommand.class),
    EXIT("7", ExitCommand.class),
    ;

    private final String input;
    private final Class<? extends Command> commandType;

    InputCommandMapper(String input, Class<? extends Command> commandType) {
        this.input = input;
        this.commandType = commandType;
    }

    public static Class<? extends Command> findCommandType(String input) {
        return Arrays.stream(values())
                .filter(mapper -> mapper.input.equals(input))
                .findFirst()
                .orElseGet(() -> EXIT)
                .commandType;
    }
}